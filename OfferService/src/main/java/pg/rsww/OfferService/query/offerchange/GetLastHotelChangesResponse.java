package pg.rsww.OfferService.query.offerchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pg.rsww.OfferService.query.offerchange.hotel.HotelRemovedEvent;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetLastHotelChangesResponse {
    @JsonProperty("hotel_changed_events")
    List<HotelRemovedEvent> hotelChangedEvents;
}
