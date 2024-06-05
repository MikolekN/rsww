package com.example.transportation.flight.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "flight_reservations")
@NoArgsConstructor
@Getter
@ToString
public class FlightReservation {
    @Id
    @JsonProperty("id")
    private UUID id;

    @Column (name = "successfully_reserved")
    @JsonProperty("successfully_reserved")
    private boolean successfullyReserved;

    @ManyToOne
    @JoinColumn(name = "flight")
    @JsonProperty("flight")
    private Flight flight;

    @Column(name = "user_id")
    @JsonProperty("user_id")
    private Long userId;

    @Column(name = "people_count")
    @JsonProperty("people_count")
    private int peopleCount;

    public FlightReservation(boolean successfullyReserved, Flight flight, Long userId, int peopleCount) {
        this.successfullyReserved = successfullyReserved;
        this.id = UUID.randomUUID();
        this.flight = flight;
        this.userId = userId;
        this.peopleCount = peopleCount;
    }
}
