package com.example.transportation.query;

import com.example.transportation.event.domain.FlightRemovedEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetLastFlightsRemovedResponse {
    @JsonProperty("flight_removed_events")
    List<FlightRemovedEvent> flightRemovedEvents;
}