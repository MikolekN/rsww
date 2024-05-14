package com.example.transportation.event.domain;

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
@Table(name = "flight_cancelled_event")
public class FlightCancelledEvent {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "reservation")
    private FlightBookedEvent reservation;
}
