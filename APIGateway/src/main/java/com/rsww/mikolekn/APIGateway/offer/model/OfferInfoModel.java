package com.rsww.mikolekn.APIGateway.offer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

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
    @AllArgsConstructor
    public static class RoomInOfferModel {
        @JsonProperty("type")
        private String type;
        @JsonProperty("price")
        private float price;
    }
}
