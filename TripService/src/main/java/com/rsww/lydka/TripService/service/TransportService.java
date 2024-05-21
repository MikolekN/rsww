package com.rsww.lydka.TripService.service;

//import com.google.common.collect.ImmutableList;
import com.rsww.lydka.TripService.listener.events.trip.reservation.transport.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransportService {
    private final Logger logger = LoggerFactory.getLogger(TransportService.class);
    private final Queue ReserveFlightQueue;
    private final Queue CancelFlightReservationQueue;
    private final RabbitTemplate template;

    @Autowired
    public TransportService(final RabbitTemplate template,
                            Queue ReserveFlightQueue,
                            Queue CancelFlightReservationQueue) {
        this.template = template;
        this.ReserveFlightQueue = ReserveFlightQueue;
        this.CancelFlightReservationQueue = CancelFlightReservationQueue;
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

}
