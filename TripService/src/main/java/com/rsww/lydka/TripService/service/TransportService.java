package com.rsww.lydka.TripService.service;

//import com.google.common.collect.ImmutableList;
import com.rsww.lydka.TripService.dto.GetFlightsRequest;
import com.rsww.lydka.TripService.listener.events.accommodation.response.GetFlightsInfoResponse;
import com.rsww.lydka.TripService.listener.events.accommodation.response.GetHotelsResponse;
import com.rsww.lydka.TripService.listener.events.trip.reservation.transport.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
public class TransportService {
    private final Logger logger = LoggerFactory.getLogger(TransportService.class);
    private final Queue ReserveFlightQueue;
    private final Queue CancelFlightReservationQueue;
    private final Queue getAllFlightsQueue;
    private final RabbitTemplate template;
    private final AsyncRabbitTemplate asyncRabbitTemplate;

    @Autowired
    public TransportService(final RabbitTemplate template,
                            Queue ReserveFlightQueue,
                            Queue CancelFlightReservationQueue,
                            Queue getAllFlightsQueue,
                            AsyncRabbitTemplate asyncRabbitTemplate) {
        this.template = template;
        this.ReserveFlightQueue = ReserveFlightQueue;
        this.CancelFlightReservationQueue = CancelFlightReservationQueue;
        this.getAllFlightsQueue = getAllFlightsQueue;
        this.asyncRabbitTemplate = asyncRabbitTemplate;
    }

    public FlightReservation reserve(String flightId, String userId, String numberOfPeople) {
        ReserveFlight reserveFlight = new ReserveFlight(flightId, Long.parseLong(userId), Integer.parseInt(numberOfPeople));

        FlightReservation reservation = template.convertSendAndReceiveAsType(
                ReserveFlightQueue.getName(),
                reserveFlight,
                new ParameterizedTypeReference<>() {}
        );

        return reservation;
    }

    public void cancel(String reservationId, int peopleCount) {
        CancelFlight cancelFlight = new CancelFlight(UUID.fromString(reservationId), peopleCount);

        String cancellation = template.convertSendAndReceiveAsType(
                CancelFlightReservationQueue.getName(),
                cancelFlight,
                new ParameterizedTypeReference<>() {}
        );
    }

    public GetFlightsInfoResponse getAllFlights(GetFlightsRequest request) {
        GetFlightsInfoResponse response = null;
        CompletableFuture<GetFlightsInfoResponse> responseCompletableFuture = asyncRabbitTemplate.convertSendAndReceiveAsType(
                getAllFlightsQueue.getName(),
                request,
                new ParameterizedTypeReference<>() {}
        );
        try {
            response = responseCompletableFuture.get();
        } catch (Exception e) {
            logger.warn("GetFlightsRequest got timeout");
            return response;
        }
        logger.info("{} getAllFlights response", response);
        return response;
    }
}
