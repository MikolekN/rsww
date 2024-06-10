package pg.rsww.OfferService.query.offerchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pg.rsww.OfferService.query.offerchange.flight.FlightRemovedEvent;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetLastFlightsRemovedResponse {
    @JsonProperty("flight_removed_events")
    List<FlightRemovedEvent> flightRemovedEvents;
}
