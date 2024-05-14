package com.rsww.mikolekn.APIGateway.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AbstractRequest {
    @JsonProperty("uuid")
    private UUID uuid;
}
