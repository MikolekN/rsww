package com.rsww.mikolekn.APIGateway.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    private String reservationId;
    private String user;
    private Boolean payed;
    private LocalDateTime reservationTime;

    private String startFlightReservation;
    private String endFlightReservation;
    private String startFlightId;
    private String endFlightId;
    private String hotelReservation;
    private String tripId; // Czy to jest potrzebne?
    private String hotelId;
    private Double price;
}