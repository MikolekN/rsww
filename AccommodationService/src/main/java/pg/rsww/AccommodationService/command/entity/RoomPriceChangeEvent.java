package pg.rsww.AccommodationService.command.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class RoomPriceChangeEvent extends Event {
    @JsonProperty("hotel_uuid")
    private UUID hotelUuid;
    @JsonProperty("room_type")
    private String roomType;
    @JsonProperty("old_price")
    private float oldPrice;
    @JsonProperty("new_price")
    private float newPrice;
    @JsonProperty("hotel_name")
    private String hotelName;
    public RoomPriceChangeEvent(UUID uuid, UUID hotelUuid, String roomType, float oldPrice, float newPrice, String hotelName) {
        super(uuid, LocalDateTime.now());
        this.hotelUuid = hotelUuid;
        this.roomType = roomType;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.hotelName = hotelName;
    }
}
