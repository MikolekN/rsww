package com.example.transportation.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "flight_reservations")
@NoArgsConstructor
@Getter
public class FlightReservation {
    @Id
    private UUID id;

    @Column (name = "successfully_reserved")
    private boolean successfullyReserved = false;

    @ManyToOne
    @JoinColumn(name = "flight")
    private Flight flight;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "number_of_people")
    private int numberOfPeople;

    public FlightReservation(Flight flight, Long userId, int numberOfPeople) {
        this.id = UUID.randomUUID();
        this.flight = flight;
        this.userId = userId;
        this.numberOfPeople = numberOfPeople;
    }
}
