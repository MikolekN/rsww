package com.rsww.lydka.TripService.listener.events.trip;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class TripDetailsRequest {
    private String tripId;
}
