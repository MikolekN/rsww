package pg.rsww.AccommodationService.query.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
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
    @JsonProperty("stars")
    private int stars;
    @JsonProperty("rooms")
    private List<RoomTypeModel> rooms;
    @JsonProperty("request_uuid")
    private UUID requestUuid;

    @Builder
    @ToString
    @Getter
    public static class RoomTypeModel {
        @JsonProperty("type")
        private String type;
        @JsonProperty("price")
        private float price;
    }
}
