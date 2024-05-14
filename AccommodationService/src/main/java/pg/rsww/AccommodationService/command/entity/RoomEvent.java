package pg.rsww.AccommodationService.command.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RoomEvent extends Event {
    @JsonProperty("room_uuid")
    private UUID roomUuid;
    @JsonProperty("event_type")
    private int eventType;
    @JsonProperty("hotel_uuid")
    private UUID hotelUuid;
    public RoomEvent(UUID uuid, UUID roomUuid, UUID hotelUuid) {
        super(uuid, LocalDateTime.now());
        this.roomUuid = roomUuid;
        this.hotelUuid = hotelUuid;
        this.eventType = 0;
    }
}
