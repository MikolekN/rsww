package pg.rsww.OfferService.query.offer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@ToString
@AllArgsConstructor
@Getter
@Setter
public class GetAllOffersRequest {

    @JsonProperty("request_uuid")
    private UUID requestUuid;
    @JsonProperty("country")
    private String country;
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;
    @JsonProperty("number_of_adults")
    private int numberOfAdults;
    @JsonProperty("number_of_children_under_10")
    private int numberOfChildrenUnder10;
    @JsonProperty("number_of_children_under_18")
    private int numberOfChildrenUnder18;
    // TODO - maybe add 'private String whereFrom' <- transportation service
}
