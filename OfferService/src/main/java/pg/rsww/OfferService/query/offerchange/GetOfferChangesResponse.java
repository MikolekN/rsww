package pg.rsww.OfferService.query.offerchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetOfferChangesResponse {
    @JsonProperty("offer_change_events")
    List<OfferChangeEvent> offerChangeEvents;
}