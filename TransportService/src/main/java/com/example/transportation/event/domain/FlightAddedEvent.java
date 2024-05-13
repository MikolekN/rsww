package com.example.transportation.event.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flight_added_event")
public class FlightAddedEvent {
    @Id
    private UUID id;

    private UUID flightId;

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

}
