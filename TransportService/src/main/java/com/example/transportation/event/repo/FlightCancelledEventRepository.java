package com.example.transportation.event.repo;

import com.example.transportation.event.domain.FlightCancelledEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlightCancelledEventRepository extends JpaRepository<FlightCancelledEvent, UUID> {
}
