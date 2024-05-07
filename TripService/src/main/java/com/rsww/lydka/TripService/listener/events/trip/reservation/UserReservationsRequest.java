package com.rsww.lydka.TripService.listener.events.trip.reservation;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class UserReservationsRequest {
    private String userId;
}
