package com.rsww.mikolekn.APIGateway.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.mikolekn.APIGateway.utils.AbstractResponse;
import lombok.*;

import java.util.List;

@ToString
@AllArgsConstructor
@Getter
@Setter
public class GetAllOrdersResponse extends AbstractResponse {
    @JsonProperty("orders")
    List<String> orders;

}
