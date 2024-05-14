package com.rsww.mikolekn.APIGateway.offer;

public record OfferDto (String hotelUuuid,
                        String dateFrom,
                        String dateTo,
                        String numberOfAdults,
                        String numberOfChildrenUnder10,
                        String numberOfChildrenUnder18) {
}
