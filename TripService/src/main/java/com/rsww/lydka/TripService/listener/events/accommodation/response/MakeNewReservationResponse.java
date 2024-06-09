package com.rsww.lydka.TripService.listener.events.accommodation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.lydka.TripService.listener.events.accommodation.event.ReservationMadeEvent;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MakeNewReservationResponse {
    @JsonProperty("is_successful")
    private boolean isSuccessful;
    @JsonProperty("reservation_made_event")
    private ReservationMadeEvent reservationMadeEvent;
}
