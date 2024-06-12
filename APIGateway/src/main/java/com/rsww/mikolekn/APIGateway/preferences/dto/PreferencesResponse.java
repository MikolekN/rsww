package com.rsww.mikolekn.APIGateway.preferences.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.mikolekn.APIGateway.preferences.model.ReservationInfo;
import com.rsww.mikolekn.APIGateway.utils.AbstractResponse;
import lombok.*;

import java.util.List;
import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PreferencesResponse extends AbstractResponse {
    @JsonProperty("preferences")
    private List<ReservationInfo> preferences;

    public PreferencesResponse(UUID uuid, boolean response, List<ReservationInfo> preferences) {
        super(uuid, response);
        this.preferences = preferences;
    }
}
