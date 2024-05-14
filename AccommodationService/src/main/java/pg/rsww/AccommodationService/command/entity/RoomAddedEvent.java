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
    @JsonProperty("capacity")
    private int capacity;
    @JsonProperty("type")
    private String type;
    @JsonProperty("base_price")
    private float basePrice;

    public RoomAddedEvent(UUID uuid, UUID roomUuid, int capacity, String type, float basePrice, UUID hotelUuid) {
        super(uuid, roomUuid, hotelUuid);
        this.capacity = capacity;
        this.type = type;
        this.basePrice = basePrice;
        this.setEventType(1);
    }
}
