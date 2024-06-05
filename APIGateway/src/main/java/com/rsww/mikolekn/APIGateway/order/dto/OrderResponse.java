package com.rsww.mikolekn.APIGateway.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.mikolekn.APIGateway.utils.AbstractResponse;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponse extends AbstractResponse {
    @JsonProperty("reservationId")
    private String reservationId;

    public OrderResponse(UUID uuid, boolean response, String reservationId) {
        super(uuid, response);
        this.reservationId = reservationId;
    }
}
