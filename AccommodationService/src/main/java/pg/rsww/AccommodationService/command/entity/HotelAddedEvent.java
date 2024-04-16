package pg.rsww.AccommodationService.command.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import pg.rsww.AccommodationService.query.entity.Hotel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class HotelAddedEvent extends HotelEvent {

    @JsonProperty("name")
    private String name;

    @JsonProperty("country")
    private String country;

    public HotelAddedEvent(UUID uuid, UUID hotelUuid, String name, String country) {
        super(uuid, hotelUuid);
        this.name = name;
        this.country = country;
        this.setEventType(1);
    }
}
