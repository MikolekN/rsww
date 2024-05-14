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
public class ReservationEvent extends Event {
    @JsonProperty("reservation_uuid")
    private UUID reservationUuid;
    @JsonProperty("event_type")
    private int eventType;

    public ReservationEvent(UUID uuid, UUID reservationUuid) {
        super(uuid, LocalDateTime.now());
        this.reservationUuid = reservationUuid;
        this.eventType = 0;
    }
}
