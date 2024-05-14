package pg.rsww.OfferService.query.transport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetFlightInfoRequest {
    @JsonProperty("flightDate")
    String flightDate;
}
