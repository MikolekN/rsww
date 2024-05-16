package com.example.transportation.flight.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Flight {

    @Id
    private UUID id;

    @Column(name = "departure_airport")
    private String departureAirport;

    @Column(name = "departure_country")
    private String departureCountry;

    @Column(name = "arrival_airport")
    private String arrivalAirport;

    @Column(name = "arrival_country")
    private String arrivalCountry;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Column(name = "travel_time")
    private int travelTime;

    @Column(name = "sits_count")
    private int sitsCount;

    @Column(name = "sits_occupied")
    private int sitsOccupied;

    private int price;

    public Flight(String departureAirport, String departureCountry, String arrivalAirport, String arrivalCountry,
                  LocalDate departureDate, LocalDate arrivalDate,
                  int travelTime, int sitsCount, int price) {
        this.id = UUID.randomUUID();
        this.departureAirport = departureAirport;
        this.departureCountry = departureCountry;
        this.arrivalAirport = arrivalAirport;
        this.arrivalCountry = arrivalCountry;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.travelTime = travelTime;
        this.sitsCount = sitsCount;
        this.sitsOccupied = 0;
        this.price = price;
    }

    public void reservePlaces(int places) {
        this.sitsOccupied += places;
    }

    public void updatePrice(int price) {
        this.price = price;
    }

    public boolean hasAvailableSits(int sitsNumber) {
        return sitsNumber <= (sitsCount - sitsOccupied);
    }
}
