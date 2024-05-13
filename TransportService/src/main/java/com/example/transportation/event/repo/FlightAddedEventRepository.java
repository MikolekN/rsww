package com.example.transportation.event.repo;

import com.example.transportation.event.domain.FlightAddedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlightAddedEventRepository extends JpaRepository<FlightAddedEvent, UUID> {
    FlightAddedEvent findByFlightId(UUID flightId);
}
