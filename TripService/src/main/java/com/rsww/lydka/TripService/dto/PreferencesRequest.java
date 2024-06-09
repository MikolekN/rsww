package com.rsww.lydka.TripService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PreferencesRequest {
    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("username")
    private String username;
}
