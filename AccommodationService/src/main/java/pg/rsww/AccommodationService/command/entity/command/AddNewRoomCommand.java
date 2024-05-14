package pg.rsww.AccommodationService.command.entity.command;

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
public class AddNewRoomCommand {
    @JsonProperty("uuid")
    private UUID uuid;
    @JsonProperty("capacity")
    private int capacity;
    @JsonProperty("type")
    private String type;
    @JsonProperty("base_price")
    private float basePrice;
    @JsonProperty("hotel_uuid")
    private UUID hotelUuid;
}
