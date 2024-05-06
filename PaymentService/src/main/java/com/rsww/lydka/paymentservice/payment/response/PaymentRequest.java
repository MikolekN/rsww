package com.rsww.lydka.paymentservice.payment.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PaymentRequest {
    private String message;

    public PaymentRequest(String message) {
        this.message = message;
    }
}
