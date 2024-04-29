package pg.rsww.OfferService.query.accommodation;

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
public class GetAllHotelsResponse {
    @JsonProperty("hotels_list")
    private List<HotelShortModel> hotelsList;
    @JsonProperty("request_uuid")
    private UUID requestUuid;

    @Builder
    @ToString
    public static class HotelShortModel {
        @JsonProperty("uuid")
        private UUID uuid;
        @JsonProperty("name")
        private String name;
        @JsonProperty("country")
        private String country;
    }
}
