package com.rsww.mikolekn.APIGateway.preferences.model;

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
    private String departureAirport;
    private String departureCountry;
    private String arrivalAirport;
    private String arrivalCountry;

    public ReservationInfo(String reservationId, String user, Boolean payed, LocalDateTime reservationTime,
                           String startFlightReservation, String endFlightReservation, String startFlightId,
                           String endFlightId, String hotelReservation, String tripId, String hotelId,
                           Integer price, String hotelName, String country, String departureAirport,
                           String departureCountry, String arrivalAirport, String arrivalCountry) {
        super(reservationId, user, payed, reservationTime, startFlightReservation, endFlightReservation,
                startFlightId, endFlightId, hotelReservation, tripId, hotelId, price);
        this.hotelName = hotelName;
        this.country = country;
        this.departureAirport = departureAirport;
        this.departureCountry = departureCountry;
        this.arrivalAirport = arrivalAirport;
        this.arrivalCountry = arrivalCountry;
    }
}
