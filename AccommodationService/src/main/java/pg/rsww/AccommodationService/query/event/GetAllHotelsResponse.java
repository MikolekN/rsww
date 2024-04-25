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
public class GetAllHotelsResponse {
    @JsonProperty("hotels_list")
    private List<HotelShortModel> hotelsList;
    @JsonProperty("request_uuid")
    private UUID requestUuid;

    @Builder
    public static class HotelShortModel {
        @JsonProperty("uuid")
        private UUID uuid;
        @JsonProperty("name")
        private String name;
        @JsonProperty("country")
        private String country;
    }
}
