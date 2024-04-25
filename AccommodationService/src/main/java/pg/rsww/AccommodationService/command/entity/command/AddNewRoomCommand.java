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
    @JsonProperty("number_of_adults")
    private int numberOfAdults;
    @JsonProperty("number_of_children")
    private int numberOfChildren;
    @JsonProperty("type")
    private String type;
    @JsonProperty("price")
    private float price;
    @JsonProperty("hotel_uuid")
    private UUID hotelUuid;
}
