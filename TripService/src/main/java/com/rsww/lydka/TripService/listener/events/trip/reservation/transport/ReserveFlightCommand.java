package com.rsww.lydka.TripService.listener.events.trip.reservation.transport;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ReserveFlightCommand {
    private String flightId;
    private String userId;
    private int numberOfPeople;
}
