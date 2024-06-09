package com.rsww.lydka.TripService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.lydka.TripService.entity.Reservation;
import lombok.*;

import java.util.List;
import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PreferencesResponse {
    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("response")
    private boolean response = false;

    @JsonProperty("preferences")
    private List<Reservation> preferences;
}
