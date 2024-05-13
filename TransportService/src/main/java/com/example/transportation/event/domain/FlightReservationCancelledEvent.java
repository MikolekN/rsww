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
@Table(name = "flight_reservation_cancelled_event")
public class FlightReservationCancelledEvent {
    @Id
    UUID id;

}
