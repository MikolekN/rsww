package com.rsww.mikolekn.APIGateway.order;

public record OrderDto(String username,
                       String flightToUuid,
                       String flightFromUuid,
                       String hotelUuid,
                       String roomType,
                       String dateFrom,
                       String dateTo,
                       String numberOfAdults,
                       String numberOfChildrenUnder10,
                       String numberOfChildrenUnder18) {
}
