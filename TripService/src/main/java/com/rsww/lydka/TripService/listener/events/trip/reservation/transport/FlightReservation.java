package com.rsww.lydka.TripService.listener.events.trip.reservation.transport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FlightReservation {
    private String id;
    private boolean successfullyReserved;
    private Flight flight;
    private String userId;
    private String peopleCount;
}