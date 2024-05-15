package com.rsww.mikolekn.APIGateway.offer.DTO;

public record OffersDto (String country,
                         String dateFrom,
                         String dateTo,
                         String numberOfAdults,
                         String numberOfChildrenUnder10,
                         String numberOfChildrenUnder18) {
}
