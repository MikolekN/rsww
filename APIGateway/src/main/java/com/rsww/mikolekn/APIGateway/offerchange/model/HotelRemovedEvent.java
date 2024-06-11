package com.rsww.mikolekn.APIGateway.offerchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class HotelRemovedEvent extends HotelEvent {
    @JsonProperty("country")
    private String country;
    @JsonProperty("name")
    private String name;
    public HotelRemovedEvent(UUID uuid, UUID hotelUuid, String country, String name) {
        super(uuid, hotelUuid);
        this.country = country;
        this.name = name;
    }
}