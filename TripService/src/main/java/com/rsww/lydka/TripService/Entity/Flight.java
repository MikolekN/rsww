package com.rsww.lydka.TripService.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Document("flights")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flight {
    @Id
    private String flightId;
    private String departureAirport;
    private String arrivalAirport;
    private String departureDate;
    private String arrivalDate;
    private int travelTime;
    private int sitsCount;
    private int sitsOccupied;
    private int price;
}
