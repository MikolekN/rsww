package pg.rsww.AccommodationService.command.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pg.rsww.AccommodationService.command.entity.HotelRemovedEvent;

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
