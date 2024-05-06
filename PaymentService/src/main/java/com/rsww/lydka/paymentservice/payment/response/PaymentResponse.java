package com.rsww.lydka.paymentservice.payment.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PaymentResponse {
    private String message;
    private boolean status;

    public PaymentResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }
}
