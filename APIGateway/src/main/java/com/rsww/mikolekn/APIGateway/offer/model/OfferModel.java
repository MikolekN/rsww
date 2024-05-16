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

    private String country;

    private LocalDate startDate;

    private LocalDate endDate;

    private int numberOfAdults;

    private int numberOfChildrenUnder10;

    private int numberOfChildrenUnder18;

    private String hotelName;

    private UUID hotelUuid;
}
