package com.example.transportation.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookFlightRequest {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("flight_id")
    private UUID flightId;
}
