package pg.rsww.ChangeOfferService.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangeFlightPriceCommand {
    @JsonProperty("uuid")
    private UUID uuid;
    @JsonProperty("changed_price")
    private float changedPrice;
}
