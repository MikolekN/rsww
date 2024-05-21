package com.rsww.lydka.TripService.listener.events.trip.reservation.transport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CancelFlight {
    @JsonProperty("reservationID")
    UUID reservationID;
    @JsonProperty("peopleCount")
    int peopleCount;
}
