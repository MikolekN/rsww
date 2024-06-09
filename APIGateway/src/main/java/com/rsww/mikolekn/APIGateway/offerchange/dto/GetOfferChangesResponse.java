package com.rsww.mikolekn.APIGateway.offerchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetOfferChangesResponse {
    @JsonProperty("offer_change_events")
    List<OfferChangeEvent> offerChangeEvents;
}