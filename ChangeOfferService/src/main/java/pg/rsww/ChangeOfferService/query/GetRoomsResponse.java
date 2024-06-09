package pg.rsww.ChangeOfferService.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pg.rsww.ChangeOfferService.entity.Hotel;
import pg.rsww.ChangeOfferService.entity.RoomType;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetRoomsResponse {
    @JsonProperty("room_types")
    List<RoomType> roomTypes;
}
