package com.rsww.lydka.TripService.listener.events.payment;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class PaymentRequest {
    private final String reservationId;
    private final String userId;
}
