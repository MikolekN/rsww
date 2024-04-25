package com.rsww.lydka.paymentms.payment.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PaymentReqeust {
    private String message;

    public PaymentReqeust(String message) {
        this.message = message;
    }
}
