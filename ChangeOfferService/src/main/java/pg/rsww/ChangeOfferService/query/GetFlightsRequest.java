package pg.rsww.ChangeOfferService.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetFlightsRequest {
    @JsonProperty("uuid")
    private UUID uuid;
}
