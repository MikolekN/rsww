package pg.rsww.OfferService.query.offerchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetLastHotelChangesRequest {
    @JsonProperty("uuid")
    private UUID uuid;
}
