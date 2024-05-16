package com.rsww.lydka.TripService.listener.events.trip.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HotelReservationResponse {
    @JsonProperty("is_successful")
    private boolean isSuccessful;
    //@JsonProperty("reservation_made_event")
    //private HotelReservationResponseInfo reservationMadeEvent;
}
