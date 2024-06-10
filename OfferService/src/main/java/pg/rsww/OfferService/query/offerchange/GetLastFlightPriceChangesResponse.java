package pg.rsww.OfferService.query.offerchange;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pg.rsww.OfferService.query.offerchange.flight.FlightPriceChangedEvent;
import pg.rsww.OfferService.query.offerchange.flight.FlightRemovedEvent;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetLastFlightPriceChangesResponse {
    @JsonProperty("flight_price_change_events")
    List<FlightPriceChangedEvent> flightPriceChangedEvents;
}
