package com.rsww.mikolekn.APIGateway.offer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.mikolekn.APIGateway.offer.model.OfferInfoModel;
import com.rsww.mikolekn.APIGateway.utils.AbstractResponse;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetOfferInfoResponse extends AbstractResponse {
    @JsonProperty("response")
    private boolean response;
    @JsonProperty("offer")
    private OfferInfoModel offer;
}
