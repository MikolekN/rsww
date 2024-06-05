package com.rsww.mikolekn.APIGateway.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
@Setter
public class GetAllOrdersRequest {
    @JsonProperty("username")
    private String username;
}
