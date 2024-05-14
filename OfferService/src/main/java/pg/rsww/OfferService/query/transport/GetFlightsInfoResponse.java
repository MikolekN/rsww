package pg.rsww.OfferService.query.transport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetFlightsInfoResponse {
    @JsonProperty("flights")
    List<Flight> flights;
}
