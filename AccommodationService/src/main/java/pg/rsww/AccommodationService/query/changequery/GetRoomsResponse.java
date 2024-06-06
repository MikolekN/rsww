package pg.rsww.AccommodationService.query.changequery;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
