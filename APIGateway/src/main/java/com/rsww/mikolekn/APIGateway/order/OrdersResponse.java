package com.rsww.mikolekn.APIGateway.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.mikolekn.APIGateway.utils.AbstractResponse;

import java.util.List;

public class OrdersResponse extends AbstractResponse {
    @JsonProperty("orders")
    private String orders;
}
