package com.rsww.lydka.TripService.listener.events.trip.reservation.transport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReserveFlight {
    @JsonProperty("flightId")
    private String flightId;
    @JsonProperty("userId")
    private long userId;
    @JsonProperty("peopleCount")
    private int peopleCount;
}
