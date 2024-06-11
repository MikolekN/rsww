package com.rsww.mikolekn.APIGateway.offerchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangeRoomPriceCommand {
    @JsonProperty("hotel_uuid")
    private UUID hotelUuid;
    @JsonProperty("room_type")
    private String roomType;
    @JsonProperty("changed_price")
    private float changedPrice;
}
