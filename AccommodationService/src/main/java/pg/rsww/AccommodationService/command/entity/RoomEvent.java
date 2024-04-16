package pg.rsww.AccommodationService.command.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
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

    public RoomEvent(UUID uuid, UUID roomUuid) {
        super(uuid, LocalDateTime.now());
        this.roomUuid = roomUuid;
        this.eventType = 0;
    }
}
