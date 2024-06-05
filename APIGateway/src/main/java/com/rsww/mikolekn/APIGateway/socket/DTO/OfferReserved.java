package com.rsww.mikolekn.APIGateway.socket.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class OfferReserved {
    String message;
    String start_date;
    String end_date;
    String hotel_uuid;
}
