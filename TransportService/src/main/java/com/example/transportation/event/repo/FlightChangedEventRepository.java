package com.example.transportation.event.repo;

import com.example.transportation.event.domain.EventTypeFlight;
import com.example.transportation.event.domain.FlightChangedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlightChangedEventRepository extends JpaRepository<FlightChangedEvent, UUID> {

}
