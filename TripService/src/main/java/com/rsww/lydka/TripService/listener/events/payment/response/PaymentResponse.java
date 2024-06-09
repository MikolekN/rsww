package com.rsww.lydka.TripService.listener.events.payment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@Jacksonized
public class PaymentResponse {
    private UUID uuid;
    private boolean response;
    private String reservationId;
}
