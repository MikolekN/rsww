package com.rsww.lydka.TripService.listener.events.accommodation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.lydka.TripService.listener.events.trip.reservation.transport.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetFlightsInfoResponse {
    @JsonProperty("flights")
    List<Flight> flights;
}
