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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;

    @Column(name = "departure_airport")
    private String departureAirport;

    @Column(name = "arrival_airport")
    private String arrivalAirport;

    @Column(name = "departure_date")
    private String departureDate;

    @Column(name = "arrival_date")
    private String arrivalDate;

    @Column(name = "travel_time")
    private int travelTime;

    @Column(name = "places_count")
    private int placesCount;

    @Column(name = "places_occupied")
    private int placesOccupied;

    private int price;

    public Flight(String departureAirport, String arrivalAirport,
                  String departureDate, String arrivalDate,
                  int travelTime, int placesCount, int price) {
        this.id = UUID.randomUUID();
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.travelTime = travelTime;
        this.placesCount = placesCount;
        this.placesOccupied = 0;
        this.price = price;
    }

    public void reservePlaces(int places) {
        this.placesOccupied += places;
    }

    public void updatePrice(int price) {
        this.price = price;
    }
}
