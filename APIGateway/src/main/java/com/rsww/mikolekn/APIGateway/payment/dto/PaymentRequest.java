package com.rsww.mikolekn.APIGateway.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.mikolekn.APIGateway.utils.AbstractRequest;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentRequest extends AbstractRequest {
    @JsonProperty("reservationId")
    private String reservationId;

    public PaymentRequest(UUID uuid, String reservationId) {
        super(uuid);
        this.reservationId = reservationId;
    }
}
