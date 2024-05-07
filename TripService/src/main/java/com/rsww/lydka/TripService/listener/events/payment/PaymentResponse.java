package com.rsww.lydka.TripService.listener.events.payment;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class PaymentResponse {
    private Boolean status;
}
