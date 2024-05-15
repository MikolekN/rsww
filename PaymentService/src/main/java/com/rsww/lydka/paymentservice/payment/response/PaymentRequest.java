package com.rsww.lydka.paymentservice.payment.response;

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

    @JsonProperty("reservationId")
    private String reservationId;
}
