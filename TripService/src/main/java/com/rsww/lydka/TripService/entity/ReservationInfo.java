package com.rsww.lydka.TripService.entity;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationInfo extends Reservation {
    private String hotelName;
    private String country;
    // TODO: add start and end flight names

    public ReservationInfo(String reservationId, String user, Boolean payed, LocalDateTime reservationTime,
                           String startFlightReservation, String endFlightReservation, String startFlightId,
                           String endFlightId, String hotelReservation, String tripId, String hotelId,
                           Integer price, String hotelName, String country) {
        super(reservationId, user, payed, reservationTime, startFlightReservation, endFlightReservation,
                startFlightId, endFlightId, hotelReservation, tripId, hotelId, price);
        this.hotelName = hotelName;
        this.country = country;
    }
}
