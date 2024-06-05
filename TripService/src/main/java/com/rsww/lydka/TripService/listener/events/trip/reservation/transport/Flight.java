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
    private UUID id;
    private String departureAirport;
    private String departureCountry;
    private String arrivalAirport;
    private String arrivalCountry;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private int travelTime;
    private int sitsCount;
    private int sitsOccupied;
    private int price;
}
