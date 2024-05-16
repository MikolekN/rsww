package com.rsww.mikolekn.APIGateway.offer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@ToString
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OfferModel {
    @JsonProperty("country")
    private String country;
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;
    @JsonProperty("number_of_adults")
    private int numberOfAdults;
    @JsonProperty("number_of_children_under_10")
    private int numberOfChildrenUnder10;
    @JsonProperty("number_of_children_under_18")
    private int numberOfChildrenUnder18;
    @JsonProperty("hotel_name")
    private String hotelName;
    @JsonProperty("hotel_uuid")
    private UUID hotelUuid;
}
