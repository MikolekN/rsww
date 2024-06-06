package com.example.transportation.event.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class FlightChangedEvent {
    @Id
    private UUID uuid;
    private LocalDateTime timeStamp;
    @JsonProperty("flight_uuid")
    private UUID flightUuid;

    @Column(name = "departure_airport")
    @JsonProperty("departure_airport")
    private String departureAirport;

    @Column(name = "arrival_airport")
    @JsonProperty("arrival_airport")
    private String arrivalAirport;

    @Column(name = "departure_date")
    @JsonProperty("departure_date")
    private String departureDate;

    @Column(name = "arrival_date")
    @JsonProperty("arrival_date")
    private String arrivalDate;
}
