package com.rsww.lydka.TripService.listener.events.accommodation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CancelReservationCommand {
    @JsonProperty("uuid")
    private UUID uuid;
    @JsonProperty("timestamp")
    private LocalDateTime timeStamp;
    @JsonProperty("reservation_to_cancel_uuid")
    private UUID reservationUuid;
}
