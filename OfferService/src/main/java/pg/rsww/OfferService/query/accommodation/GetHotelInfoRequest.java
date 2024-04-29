package pg.rsww.OfferService.query.accommodation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetHotelInfoRequest {

    @JsonProperty("request_uuid")
    private UUID requestUuid;
    @JsonProperty("hotel_uuid")
    private UUID hotelUuid;
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;
    @JsonProperty("number_of_adults")
    private int numberOfAdults;
    @JsonProperty("number_of_children")
    private int numberOfChildren;
}
