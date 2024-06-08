package pg.rsww.OfferService.query.offerchange.flight;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightPriceChangedEvent extends FlightChangedEvent{
    @JsonProperty("old_price")
    private int oldPrice;
    @JsonProperty("new_price")
    private int newPrice;
}
