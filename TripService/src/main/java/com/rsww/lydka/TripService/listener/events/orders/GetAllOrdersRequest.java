package com.rsww.lydka.TripService.listener.events.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetAllOrdersRequest {
    @JsonProperty("username")
    private String username;
}
