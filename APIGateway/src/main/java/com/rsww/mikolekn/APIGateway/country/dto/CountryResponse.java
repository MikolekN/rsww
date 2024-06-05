package com.rsww.mikolekn.APIGateway.country.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.mikolekn.APIGateway.utils.AbstractResponse;
import lombok.*;

import java.util.List;
import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CountryResponse extends AbstractResponse {
    @JsonProperty("countries")
    private List<String> countries;

    public CountryResponse(UUID uuid, boolean response, List<String> countries) {
        super(uuid, response);
        this.countries = countries;
    }
}
