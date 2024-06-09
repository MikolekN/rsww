package com.rsww.mikolekn.APIGateway.offerchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class HotelEvent extends Event {
    @JsonProperty("hotel_uuid")
    private UUID hotelUuid;
    @JsonProperty("event_type")
    private int eventType;

    public HotelEvent(UUID uuid, UUID hotelUuid) {
        super(uuid, LocalDateTime.now());
        this.hotelUuid = hotelUuid;
        this.eventType = 0;
    }
}

