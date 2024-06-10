package com.example.transportation.event.domain;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
public class FlightRemovedEvent extends FlightChangedEvent {
    @Builder
    public FlightRemovedEvent(UUID uuid, LocalDateTime timeStamp, UUID flightUuid, String departureCountry, String arrivalCountry, String departureDate, String arrivalDate) {
        super(uuid, timeStamp, flightUuid, departureCountry, arrivalCountry, departureDate, arrivalDate);
    }
}
