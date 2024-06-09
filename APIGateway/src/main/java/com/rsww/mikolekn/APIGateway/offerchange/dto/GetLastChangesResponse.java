package com.rsww.mikolekn.APIGateway.offerchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.mikolekn.APIGateway.utils.AbstractResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetLastChangesResponse extends AbstractResponse {
    @JsonProperty("offer_change_events")
    List<OfferChangeEvent> offerChangeEvents;
}
