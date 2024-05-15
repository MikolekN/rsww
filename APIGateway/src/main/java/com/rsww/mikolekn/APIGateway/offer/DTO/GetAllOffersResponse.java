package com.rsww.mikolekn.APIGateway.offer.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.mikolekn.APIGateway.offer.model.OfferModel;
import com.rsww.mikolekn.APIGateway.utils.AbstractResponse;
import lombok.*;

import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetAllOffersResponse extends AbstractResponse
{
    @JsonProperty("response")
    private boolean response;
    @JsonProperty("offers")
    private List<OfferModel> offers;
}