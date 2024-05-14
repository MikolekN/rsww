package pg.rsww.AccommodationService.query.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CountryResponse {
    @JsonProperty("countries")
    private List<String> countries;

    public CountryResponse(List<String> countries) {
        this.countries = countries;
    }
}
