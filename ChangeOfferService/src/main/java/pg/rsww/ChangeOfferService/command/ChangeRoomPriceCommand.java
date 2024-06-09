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
public class ChangeRoomPriceCommand {
    @JsonProperty("hotel_uuid")
    private UUID hotelUuid;
    @JsonProperty("room_type")
    private String roomType;
    @JsonProperty("changed_price")
    private float changedPrice;
}
