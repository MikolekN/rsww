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
public class RoomPriceChangeEvent extends Event {
    @JsonProperty("hotel_uuid")
    private UUID hotelUuid;
    @JsonProperty("room_type")
    private String roomType;
    @JsonProperty("old_price")
    private float oldPrice;
    @JsonProperty("new_price")
    private float newPrice;

    public RoomPriceChangeEvent(UUID uuid, UUID hotelUuid, String roomType, float oldPrice, float newPrice) {
        super(uuid, LocalDateTime.now());
        this.hotelUuid = hotelUuid;
        this.roomType = roomType;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }
}