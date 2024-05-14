package com.rsww.mikolekn.APIGateway.offer;

public record OffersDto (String country,
                         String dateFrom,
                         String dateTo,
                         String numberOfAdults,
                         String numberOfChildrenUnder10,
                         String numberOfChildrenUnder18) {
}
