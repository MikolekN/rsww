package com.rsww.mikolekn.APIGateway.preferences.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.mikolekn.APIGateway.utils.AbstractRequest;

import java.util.UUID;

public class PreferencesRequest extends AbstractRequest {
    @JsonProperty("username")
    private String username;

    public PreferencesRequest(UUID uuid, String username) {
        super(uuid);
        this.username = username;
    }
}
