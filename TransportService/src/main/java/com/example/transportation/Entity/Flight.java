package com.example.transportation.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private UUID flightCode;

    private String departureAirport;
    private String arrivalAirport;
    private String flightLength;
    private int totalSitsNumber;
    private int availableSitsNumber;
    private int costPerSit;
}
