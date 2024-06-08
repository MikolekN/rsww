package com.rsww.mikolekn.APIGateway.offerchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OfferChangeEvent {
    @JsonProperty("uuid")
    private UUID uuid;
    @JsonProperty("time_stamp")
    private LocalDateTime timeStamp;
    @JsonProperty("content")
    private String content;
}
