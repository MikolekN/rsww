package com.example.transportation.query;

import com.example.transportation.event.domain.FlightPriceChangedEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetLastFlightPriceChangesResponse {
    @JsonProperty("flight_price_change_events")
    List<FlightPriceChangedEvent> flightPriceChangedEvents;
}
