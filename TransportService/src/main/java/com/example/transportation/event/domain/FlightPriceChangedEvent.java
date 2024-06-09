package com.example.transportation.event.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@ToString
@AllArgsConstructor
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
}
