package com.rsww.lydka.TripService.listener.events.trip.reservation.accommodation;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CancelHotelReservation {
    private String reservationId;
}
