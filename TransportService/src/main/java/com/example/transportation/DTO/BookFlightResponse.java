package com.example.transportation.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@ToString
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookFlightResponse {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("flight_id")
    private UUID flightId;
    @JsonProperty("successfull")
    boolean successfull;
}
