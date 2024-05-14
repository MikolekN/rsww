package pg.rsww.OfferService.query.country;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@ToString
public class CountryResponse {
    @JsonProperty("countries")
    private List<String> countries;

    public CountryResponse(List<String> countries) {
        this.countries = countries;
    }
}
