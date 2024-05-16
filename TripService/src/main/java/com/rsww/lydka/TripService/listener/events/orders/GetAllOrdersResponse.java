package com.rsww.lydka.TripService.listener.events.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    List<String> orders;

}
