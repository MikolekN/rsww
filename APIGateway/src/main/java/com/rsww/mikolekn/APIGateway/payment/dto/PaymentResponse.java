package com.rsww.mikolekn.APIGateway.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.mikolekn.APIGateway.utils.AbstractResponse;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentResponse extends AbstractResponse {
    @JsonProperty("reservationId")
    private String reservationId;
}
