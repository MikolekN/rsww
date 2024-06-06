package com.example.transportation.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetLastFlightChangesRequest {
    @JsonProperty("uuid")
    private UUID uuid;
}
