package pg.rsww.AccommodationService.query.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class CountryRequest {
    @JsonProperty("uuid")
    private UUID uuid;
    public CountryRequest(UUID uuid) {
        this.uuid = uuid;
    }
}
