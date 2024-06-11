package pg.rsww.AccommodationService.command.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
public class HotelRemovedEvent extends HotelEvent {
    @JsonProperty("country")
    private String country;
    @JsonProperty("name")
    private String name;
    public HotelRemovedEvent(UUID uuid, UUID hotelUuid, String country, String name) {
        super(uuid, hotelUuid);
        this.country = country;
        this.name = name;
    }
}
