package com.rsww.lydka.TripService.listener.events.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.lydka.TripService.repository.ReservationRepository;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderInfoResponse {
    @JsonProperty("order")
    ReservationRepository.Reservation order;
}
