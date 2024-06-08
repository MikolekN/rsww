package pg.rsww.OfferService.query.offerchange.flight;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class FlightChangedEvent {
    private UUID uuid;
    private LocalDateTime timeStamp;
    @JsonProperty("flight_uuid")
    private UUID flightUuid;

    @JsonProperty("departure_country")
    private String departureCountry;

    @JsonProperty("arrival_country")
    private String arrivalCountry;

    @JsonProperty("departure_date")
    private String departureDate;

    @JsonProperty("arrival_date")
    private String arrivalDate;
}
