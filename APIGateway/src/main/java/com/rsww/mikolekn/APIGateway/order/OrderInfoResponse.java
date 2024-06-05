package com.rsww.mikolekn.APIGateway.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.mikolekn.APIGateway.utils.AbstractResponse;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderInfoResponse extends AbstractResponse {
    @JsonProperty("order")
    Reservation order;
}
