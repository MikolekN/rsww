package com.rsww.mikolekn.APIGateway.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentRequest {
    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("paymentId")
    private String paymentId;
}