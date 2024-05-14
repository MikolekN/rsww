package pg.rsww.OfferService.query.offer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetOfferInfoResponse {
    @JsonProperty("response")
    private boolean response;
    @JsonProperty("offer")
    private OfferInfoModel offer;
}
