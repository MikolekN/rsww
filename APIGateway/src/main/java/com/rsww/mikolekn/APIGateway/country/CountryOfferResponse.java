package com.rsww.mikolekn.APIGateway.country;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@ToString
@Getter
public class CountryOfferResponse {
    @JsonProperty("countries")
    private List<String> countries;

    public CountryOfferResponse(List<String> countries) {
        this.countries = countries;
    }
}