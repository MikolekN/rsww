package com.example.transportation.Repository;

import com.example.transportation.Entity.FlightReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlightReservationRepository extends JpaRepository<FlightReservation, UUID> {
}
