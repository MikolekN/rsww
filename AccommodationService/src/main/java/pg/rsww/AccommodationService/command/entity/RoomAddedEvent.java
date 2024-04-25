package pg.rsww.AccommodationService.command.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class RoomAddedEvent extends RoomEvent {
    @JsonProperty("number_of_adults")
    private int numberOfAdults;
    @JsonProperty("number_of_children")
    private int numberOfChildren;
    @JsonProperty("type")
    private String type;
    @JsonProperty("price")
    private float price;

    public RoomAddedEvent(UUID uuid, UUID roomUuid, int numberOfAdults, int numberOfChildren, String type, float price, UUID hotelUuid) {
        super(uuid, roomUuid, hotelUuid);
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.type = type;
        this.price = price;
        this.setEventType(1);
    }
}
