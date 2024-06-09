package pg.rsww.OfferService.query.offerchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pg.rsww.OfferService.query.offerchange.room.RoomPriceChangeEvent;

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