package com.example.transportation.event.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
public class FlightPriceChangedEvent extends FlightChangedEvent{
    @Column(name = "old_price")
    @JsonProperty("old_price")
    private int oldPrice;
    @Column(name = "new_price")
    @JsonProperty("new_price")
    private int newPrice;

    @Builder
    public FlightPriceChangedEvent(UUID uuid, LocalDateTime timeStamp, UUID flightUuid, String departureCountry, String arrivalCountry, String departureDate, String arrivalDate, int oldPrice, int newPrice) {
        super(uuid, timeStamp, flightUuid, departureCountry, arrivalCountry, departureDate, arrivalDate);
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }
}
