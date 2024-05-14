package com.rsww.mikolekn.APIGateway.country;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.mikolekn.APIGateway.utils.AbstractResponse;

import java.util.List;
import java.util.UUID;

public class CountryResponse extends AbstractResponse {
    @JsonProperty("countries")
    private List<String> countries;

    CountryResponse(UUID uuid, Boolean response, List<String> countries) {
        super(uuid, response);
        this.countries = countries;
    }
}
