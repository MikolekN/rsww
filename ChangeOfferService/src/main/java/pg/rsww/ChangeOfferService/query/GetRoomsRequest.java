package pg.rsww.ChangeOfferService.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetRoomsRequest {
    @JsonProperty("uuid")
    private UUID uuid;
    @JsonProperty("hotel_uuid")
    private String hotelUuid;
}
