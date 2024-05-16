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

    private String hotelName;

    private String country;

    private int stars;

    private List<RoomInOfferModel> rooms;

    private List<Flight> availableFlightsTo;

    private List<Flight> availableFlightsFrom;

    @Builder
    @ToString
    @Getter
    @AllArgsConstructor
    public static class RoomInOfferModel {
        private String type;
        private float price;
    }
}
