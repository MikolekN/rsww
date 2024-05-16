package com.rsww.lydka.TripService.listener.events.trip.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostReservationResponse {
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("response")
    private boolean response;
    @JsonProperty("reservationId")
    private String reservationId;
}
