package com.example.transportation.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
    private String departureDate;

    @Column(name = "arrival_date")
    private String arrivalDate;

    @Column(name = "travel_time")
    private int travelTime;

    @Column(name = "sits_count")
    private int sitsCount;

    @Column(name = "sits_occupied")
    private int sitsOccupied;

    private int price;

    public Flight(String departureAirport, String departureCountry, String arrivalAirport, String arrivalCountry,
                  String departureDate, String arrivalDate,
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
