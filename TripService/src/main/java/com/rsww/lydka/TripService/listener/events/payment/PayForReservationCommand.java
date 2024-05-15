package com.rsww.lydka.TripService.listener.events.payment;


import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@Builder
@Jacksonized
public class PayForReservationCommand {
    private final UUID uuid;
    private final String reservationId;
}