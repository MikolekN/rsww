package com.rsww.mikolekn.APIGateway.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.mikolekn.APIGateway.utils.AbstractRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@ToString
@AllArgsConstructor
@Getter
@Setter
public class GetOrderInfoRequest extends AbstractRequest {
    @JsonProperty("reservationId")
    private String reservationId;

    GetOrderInfoRequest (UUID uuid, String reservationId) {
        super(uuid);
        this.reservationId = reservationId;
    }
}
