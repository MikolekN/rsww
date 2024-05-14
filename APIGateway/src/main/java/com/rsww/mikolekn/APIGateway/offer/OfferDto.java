package com.rsww.mikolekn.APIGateway.offer;

public record OfferDto (String hotelUuid,
                        String dateFrom,
                        String dateTo,
                        String numberOfAdults,
                        String numberOfChildrenUnder10,
                        String numberOfChildrenUnder18) {
}
