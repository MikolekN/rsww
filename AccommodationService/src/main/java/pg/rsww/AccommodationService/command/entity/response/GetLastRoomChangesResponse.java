package pg.rsww.AccommodationService.command.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pg.rsww.AccommodationService.command.entity.HotelRemovedEvent;
import pg.rsww.AccommodationService.command.entity.RoomPriceChangeEvent;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetLastRoomChangesResponse {
    @JsonProperty("room_changed_events")
    List<RoomPriceChangeEvent> roomChangedEvents;
}