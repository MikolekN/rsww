package pg.rsww.AccommodationService.query.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@ToString
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetHotelInfoResponse {
    @JsonProperty("result_found")
    private boolean resultFound;
    @JsonProperty("hotel_uuid")
    private UUID hotelUuid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("country")
    private String country;
    @JsonProperty("room_types")
    private List<String> roomTypes;
    @JsonProperty("request_uuid")
    private UUID requestUuid;
}
