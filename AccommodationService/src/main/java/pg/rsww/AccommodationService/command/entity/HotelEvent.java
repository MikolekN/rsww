package pg.rsww.AccommodationService.command.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
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
@Builder
public class HotelEvent extends Event {
    @JsonProperty("hotel_uuid")
    private UUID hotelUuid;
    @JsonProperty("event_type")
    private int eventType;

    public HotelEvent(UUID uuid, UUID hotelUuid) {
        super(uuid, LocalDateTime.now());
        this.hotelUuid = hotelUuid;
        this.eventType = 0;
    }
}
