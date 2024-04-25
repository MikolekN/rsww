package pg.rsww.AccommodationService.query.event;

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
public class GetAllHotelsRequest {

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
    @JsonProperty("number_of_children")
    private int numberOfChildren;
}
