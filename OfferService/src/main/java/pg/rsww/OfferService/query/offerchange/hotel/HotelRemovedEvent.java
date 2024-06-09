package pg.rsww.OfferService.query.offerchange.hotel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class HotelRemovedEvent extends HotelEvent {
    @JsonProperty("country")
    private String country;
    public HotelRemovedEvent(UUID uuid, UUID hotelUuid, String country) {
        super(uuid, hotelUuid);
        this.country = country;
    }
}