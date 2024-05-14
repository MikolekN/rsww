package pg.rsww.OfferService.query.offer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pg.rsww.OfferService.query.accommodation.GetAllHotelsResponse;

import java.util.List;
import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetAllOffersResponse
{
    @JsonProperty("response")
    private boolean response;
    @JsonProperty("offers")
    private List<OfferModel> offers;
}
