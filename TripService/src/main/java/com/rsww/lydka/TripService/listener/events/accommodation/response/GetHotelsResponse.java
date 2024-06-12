package com.rsww.lydka.TripService.listener.events.accommodation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.lydka.TripService.entity.Hotel;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetHotelsResponse {
    @JsonProperty("hotels")
    List<Hotel> hotels;
}
