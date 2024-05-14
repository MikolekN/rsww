package com.rsww.mikolekn.APIGateway.offer;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Flight {

    private UUID id;

    private String departureAirport;

    private String departureCountry;

    private String arrivalAirport;

    private String arrivalCountry;

    private String departureDate;

    private String arrivalDate;

    private int travelTime;

    private int sitsCount;

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

