package com.rsww.mikolekn.APIGateway.payment;

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
    @JsonProperty("paymentId")
    private String paymentId;

    public PaymentRequest(UUID uuid, String paymentId) {
        super(uuid);
        this.paymentId = paymentId;
    }
}