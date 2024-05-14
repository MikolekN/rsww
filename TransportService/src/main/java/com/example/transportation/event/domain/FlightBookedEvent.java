package com.example.transportation.event.domain;

import com.example.transportation.event.domain.FlightAddedEvent;
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
@Table(name = "flight_booked_event")
public class FlightBookedEvent {
    @Id
    private UUID id;

    @Column(name = "reservation_id")
    private UUID reservationId;

    @ManyToOne
    @JoinColumn(name = "flight")
    private FlightAddedEvent flight;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "people_count")
    private int peopleCount;
}
