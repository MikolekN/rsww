package pg.rsww.OfferService.query.offerchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pg.rsww.OfferService.query.offerchange.flight.FlightChangedEvent;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetLastFlightChangesResponse {
    @JsonProperty("flight_changed_events")
    List<FlightChangedEvent> flightChangedEvents;
}
