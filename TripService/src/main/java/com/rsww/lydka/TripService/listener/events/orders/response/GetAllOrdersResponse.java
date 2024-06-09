package com.rsww.lydka.TripService.listener.events.orders.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.lydka.TripService.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@AllArgsConstructor
@Getter
@Setter
public class GetAllOrdersResponse {
    @JsonProperty("orders")
    List<Reservation> orders;

}
