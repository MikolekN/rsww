package com.example.transportation.query;

import com.example.transportation.event.domain.FlightChangedEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetLastFlightChangesResponse {
    @JsonProperty("flight_changed_events")
    List<FlightChangedEvent> flightChangedEvents;
}
