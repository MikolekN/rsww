package com.rsww.mikolekn.APIGateway.offerchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RemoveHotelCommand {
    @JsonProperty("uuid")
    private UUID uuid;

}
