package com.example.transportation.DTO;

import com.example.transportation.flight.domain.Flight;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetFlightsInfoResponse {
    @JsonProperty("flights")
    List<Flight> flights;
}
