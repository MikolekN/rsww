package com.rsww.mikolekn.APIGateway.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsww.mikolekn.APIGateway.utils.AbstractResponse;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentResponse extends AbstractResponse {
    @JsonProperty("paymentId")
    private String paymentId;
}
