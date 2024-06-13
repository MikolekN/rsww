package com.example.transportation.flight.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
