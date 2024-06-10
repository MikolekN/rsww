package com.example.transportation.event.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@ToString
@Getter
@Setter
public class FlightChangedEvent {
    @Id
    private UUID uuid;
    private LocalDateTime timeStamp;
    @JsonProperty("flight_uuid")
    private UUID flightUuid;

    @Column(name = "departure_country")
    @JsonProperty("departure_country")
    private String departureCountry;

    @Column(name = "arrival_country")
    @JsonProperty("arrival_country")
    private String arrivalCountry;

    @Column(name = "departure_date")
    @JsonProperty("departure_date")
    private String departureDate;

    @Column(name = "arrival_date")
    @JsonProperty("arrival_date")
    private String arrivalDate;

    public FlightChangedEvent(UUID uuid, LocalDateTime timeStamp, UUID flightUuid, String departureCountry, String arrivalCountry, String departureDate, String arrivalDate) {
        this.uuid = uuid;
        this.timeStamp = timeStamp;
        this.flightUuid = flightUuid;
        this.departureCountry = departureCountry;
        this.arrivalCountry = arrivalCountry;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }
}
