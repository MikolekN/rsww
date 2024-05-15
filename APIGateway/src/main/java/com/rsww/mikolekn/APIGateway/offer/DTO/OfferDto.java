package com.rsww.mikolekn.APIGateway.offer.DTO;

public record OfferDto (String hotelUuid,
                        String dateFrom,
                        String dateTo,
                        String numberOfAdults,
                        String numberOfChildrenUnder10,
                        String numberOfChildrenUnder18) {
}
