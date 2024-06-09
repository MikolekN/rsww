package com.rsww.lydka.TripService.listener.events.accommodation.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@ToString
@Getter
@NoArgsConstructor
@Setter
public class ReservationCancelledEvent extends ReservationEvent {
    public ReservationCancelledEvent(UUID uuid, UUID reservationUuid) {
        super(uuid, reservationUuid);
        setEventType(2);
    }
}
