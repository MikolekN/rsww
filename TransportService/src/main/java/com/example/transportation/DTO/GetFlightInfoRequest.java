package com.example.transportation.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GetFlightInfoRequest {
    @JsonProperty("flightDate")
    String flightDate;
}
