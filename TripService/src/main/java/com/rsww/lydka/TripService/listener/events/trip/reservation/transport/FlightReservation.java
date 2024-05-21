package com.rsww.lydka.TripService.listener.events.trip.reservation.transport;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FlightReservation {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("successfully_reserved")
    private boolean successfullyReserved;
    @JsonProperty("flight")
    private Flight flight;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("people_count")
    private int peopleCount;
}