package com.rsww.lydka.TripService.listener.events.trip.reservation.transport;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CancelFlightReservationCommand {
    private long reservationId;
}
