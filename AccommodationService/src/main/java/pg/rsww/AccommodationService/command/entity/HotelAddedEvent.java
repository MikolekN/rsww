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

    @JsonProperty("stars")
    private int stars;

    public HotelAddedEvent(UUID uuid, UUID hotelUuid, String name, String country, int stars) {
        super(uuid, hotelUuid);
        this.name = name;
        this.country = country;
        this.stars = stars;
        this.setEventType(1);
    }

}
