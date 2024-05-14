package pg.rsww.OfferService.query.offer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pg.rsww.OfferService.query.accommodation.GetAllHotelsResponse;
import pg.rsww.OfferService.query.transport.Flight;

import java.util.List;
import java.util.UUID;

@ToString
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OfferInfoModel {
    @JsonProperty("hotel_name")
    private String hotelName;
    @JsonProperty("country")
    private String country;
    @JsonProperty("stars")
    private int stars;
    @JsonProperty("rooms")
    private List<RoomInOfferModel> rooms;
    @JsonProperty("available_flights_to")
    private List<Flight> availableFlightsTo;
    @JsonProperty("available_flights_from")
    private List<Flight> availableFlightsFrom;

    @Builder
    @ToString
    @Getter
    @Setter
    @AllArgsConstructor
    public static class RoomInOfferModel {
        @JsonProperty("type")
        private String type;
        @JsonProperty("price")
        private float price;
    }
}
