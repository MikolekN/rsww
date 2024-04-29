package pg.rsww.OfferService.query.offer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetOfferInfoResponse {
    @JsonProperty("request_uuid")
    private UUID requestUuid;
}
