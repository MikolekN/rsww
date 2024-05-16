package com.rsww.lydka.TripService.listener.events.trip.reservation.transport;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    private String id;
    private String departureAirport;
    private String departureCountry;
    private String arrivalAirport;
    private String arrivalCountry;
    private String departureDate;
    private String arrivalDate;
    private String travelTime;
    private String sitsCount;
    private String sitsOccupied;
    private String price;
}
