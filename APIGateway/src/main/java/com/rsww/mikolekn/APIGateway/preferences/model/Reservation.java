package com.rsww.mikolekn.APIGateway.preferences.model;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    private String tripId;
    private String hotelId;
    private Integer price;
}