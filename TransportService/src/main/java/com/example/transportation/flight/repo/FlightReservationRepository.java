package com.example.transportation.flight.repo;

import com.example.transportation.flight.domain.FlightReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlightReservationRepository extends JpaRepository<FlightReservation, UUID> {
}
