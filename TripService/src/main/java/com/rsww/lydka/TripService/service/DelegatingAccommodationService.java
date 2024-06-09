package com.rsww.lydka.TripService.service;

import com.rsww.lydka.TripService.listener.events.accommodation.command.CancelReservationCommand;
import com.rsww.lydka.TripService.listener.events.accommodation.command.MakeNewReservationCommand;
import com.rsww.lydka.TripService.listener.events.accommodation.event.ReservationCancelledEvent;
import com.rsww.lydka.TripService.listener.events.accommodation.response.MakeNewReservationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class DelegatingAccommodationService {
    private final Logger logger = LoggerFactory.getLogger(DelegatingAccommodationService.class);
    private final String reserveHotelQueueName;
    private final String cancelHotelReservationQueueName;
    private final AsyncRabbitTemplate rabbitTemplate;

    @Autowired
    public DelegatingAccommodationService(final AsyncRabbitTemplate rabbitTemplate,
                                  @Value("${spring.rabbitmq.queue.reservationMakeQueue}") final String reserveHotelQueueName,
                                  @Value("${spring.rabbitmq.queue.reservationCancelQueue}") final String cancelHotelReservationQueueName) {
        this.rabbitTemplate = rabbitTemplate;
        this.reserveHotelQueueName = reserveHotelQueueName;
        this.cancelHotelReservationQueueName = cancelHotelReservationQueueName;
    }

    public MakeNewReservationResponse reserve(String reservationId,
                                              String timestamp,
                                              String hotelId,
                                              String roomType,
                                              final String startDate,
                                              final String endDate,
                                              final String numberOfAdults,
                                              final String numberOfChildrenUnder10,
                                              final String numberOfChildrenUnder18) {

        MakeNewReservationCommand request = new MakeNewReservationCommand(UUID.fromString(reservationId),
                LocalDateTime.parse(timestamp),
                LocalDate.parse(startDate),
                LocalDate.parse(endDate),
                roomType,
                Integer.parseInt(numberOfAdults),
                Integer.parseInt(numberOfChildrenUnder10),
                Integer.parseInt(numberOfChildrenUnder18),
                UUID.fromString(hotelId));

        MakeNewReservationResponse response = null;

        CompletableFuture<MakeNewReservationResponse> responseCompletableFuture = rabbitTemplate.convertSendAndReceiveAsType(
                reserveHotelQueueName,
                request,
                new ParameterizedTypeReference<>() {}
        );
        try {
            response = responseCompletableFuture.get();
        } catch (Exception e) {
            logger.warn("MakeNewReservationRequest got timeout");
        }
        return response;
    }

    public ReservationCancelledEvent cancelReservation(String uuid,
                                                       String timestamp,
                                                       String reservationId) {
        ReservationCancelledEvent response = null;
        CancelReservationCommand request = new CancelReservationCommand(UUID.fromString(uuid), LocalDateTime.parse(timestamp), UUID.fromString(reservationId));
        CompletableFuture<ReservationCancelledEvent> responseCompletableFuture = rabbitTemplate.convertSendAndReceiveAsType(
                cancelHotelReservationQueueName,
                request,
                new ParameterizedTypeReference<>() {}
        );
        try {
            response = responseCompletableFuture.get();
        } catch (Exception e) {
            logger.warn("CancelReservationRequest got timeout");
        }
        return response;
    }
}
