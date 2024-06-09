package pg.rsww.ChangeOfferService.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@ToString
@AllArgsConstructor
@Getter
@Setter
public class ChangeFlightPriceCommand {
    @JsonProperty("uuid")
    private UUID uuid;
    @JsonProperty("changed_price")
    private float changedPrice;
}
