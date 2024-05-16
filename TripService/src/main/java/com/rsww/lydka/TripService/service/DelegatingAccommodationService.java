package com.rsww.lydka.TripService.service;

//import com.google.common.collect.ImmutableList;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.lydka.TripService.listener.events.accommodation.*;
import com.rsww.lydka.TripService.listener.events.trip.reservation.accommodation.CancelHotelReservationCommand;
import com.rsww.lydka.TripService.listener.events.trip.reservation.accommodation.ReserveHotelCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import com.rsww.lydka.TripService.entity.Hotel;
import com.rsww.lydka.TripService.listener.events.trip.reservation.PostReservationRequest;
import com.rsww.lydka.TripService.listener.events.trip.reservation.accommodation.ReservationResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

@Component
public class DelegatingAccommodationService {
    private final Logger logger = LoggerFactory.getLogger(DelegatingAccommodationService.class);
    private final String allHotelsQueueName;
    private final String getHotelDetailsQueueName;
    private final String reserveHotelQueueName;
    private final String cancelHotelReservationQueueName;
    private final AsyncRabbitTemplate rabbitTemplate;

    @Autowired
    public DelegatingAccommodationService(final AsyncRabbitTemplate rabbitTemplate,
                                  @Value("${spring.rabbitmq.queue.hotelAllRequestQueue}") final String allHotelsQueueName,
                                  @Value("${spring.rabbitmq.queue.hotelInfoRequestQueue}") final String getHotelDetailsQueueName,
                                  @Value("${spring.rabbitmq.queue.reservationMakeQueue}") final String reserveHotelQueueName,
                                  @Value("${spring.rabbitmq.queue.reservationCancelQueue}") final String cancelHotelReservationQueueName) {
        this.rabbitTemplate = rabbitTemplate;
        this.allHotelsQueueName = allHotelsQueueName;
        this.getHotelDetailsQueueName = getHotelDetailsQueueName;
        this.reserveHotelQueueName = reserveHotelQueueName;
        this.cancelHotelReservationQueueName = cancelHotelReservationQueueName;
    }

    public List<Hotel> getHotels(SearchParams params) {
        final var request = SearchParams.toRequest().apply(params);
        final CompletableFuture<GetHotelsResponse> completableRequest = rabbitTemplate.convertSendAndReceiveAsType(
                allHotelsQueueName,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        try {
            final var response = completableRequest.get();
            final var hotels = GetHotelsResponse.dtoToEntityMapper().apply(response);
            logger.debug("Fetched {} hotels.", hotels.size());
            return hotels;
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Cannot get all hotels from hotel service.", e);
        }
        return List.of(); // TODO: tutaj mam zwykła liste bo ImmutableList import nie dzialal
    }

    public Optional<Hotel> getHotel(String id) {
        final var request = GetHotelRequest.builder().hotelid(id).build();
        final CompletableFuture<HotelDetailsResponse> completableRequest = rabbitTemplate.convertSendAndReceiveAsType(
                getHotelDetailsQueueName,
                request,
                new ParameterizedTypeReference<>() {
                }
        );

        try {
            final var response = completableRequest.get();
            final var hotel = HotelDetailsResponse.dtoToEntityMapper().apply(response);
            logger.debug("Fetched hotel {}", hotel);
            return Optional.of(hotel);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error when fetching hotel {} details.", id, e);
        }
        return Optional.empty();
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


    @Data
    @Builder
    public static class SearchParams {
        private String destination;
        private String departure;
        private Integer adults;
        private Integer people3To9;
        private Integer people10To17;

        public static Function<SearchParams, GetHotelsRequest> toRequest() {
            return params -> GetHotelsRequest.builder()
                    .destination(params.destination)
                    .adults(params.adults)
                    .people3To9(params.people3To9)
                    .people10To17(params.people10To17)
                    .build();
        }
    }
}
