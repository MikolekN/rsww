package pg.rsww.AccommodationService.command.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.UUID;

@ToString
@Getter
@NoArgsConstructor
@Setter
@Entity
public class ReservationCancelledEvent extends ReservationEvent {
    public ReservationCancelledEvent(UUID uuid, UUID reservationUuid) {
        super(uuid, reservationUuid);
        setEventType(2);
    }
}
