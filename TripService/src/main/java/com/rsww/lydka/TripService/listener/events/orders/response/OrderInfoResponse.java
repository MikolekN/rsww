package com.rsww.lydka.TripService.listener.events.orders.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.lydka.TripService.entity.Reservation;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderInfoResponse {
    @JsonProperty("order")
    Reservation order;
}
