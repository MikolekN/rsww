package com.rsww.lydka.TripService.listener.events.orders.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderInfoRequest {
    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("reservationId")
    private String reservationId;
}
