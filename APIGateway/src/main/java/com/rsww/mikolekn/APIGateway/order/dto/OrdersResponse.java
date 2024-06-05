package com.rsww.mikolekn.APIGateway.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.mikolekn.APIGateway.utils.AbstractResponse;

public class OrdersResponse extends AbstractResponse {
    @JsonProperty("orders")
    private String orders;
}
