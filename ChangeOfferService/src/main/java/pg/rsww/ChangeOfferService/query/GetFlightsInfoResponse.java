package pg.rsww.ChangeOfferService.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pg.rsww.ChangeOfferService.entity.Flight;

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
