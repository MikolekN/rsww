package pg.rsww.AccommodationService.command.entity;

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
public class HotelRemovedEvent extends HotelEvent {
    public HotelRemovedEvent(UUID uuid, UUID hotelUuid) {
        super(uuid, hotelUuid);
    }
}
