package com.example.transportation.event.repo;

import com.example.transportation.event.domain.EventTypeFlight;
import com.example.transportation.event.domain.FlightChangedEvent;
import com.example.transportation.event.domain.FlightPriceChangedEvent;
import com.example.transportation.event.domain.FlightRemovedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FlightChangedEventRepository extends JpaRepository<FlightChangedEvent, UUID> {
    @Query("select e from FlightRemovedEvent e")
    List<FlightRemovedEvent> findAllFlightRemovedEvents();

    @Query("select e from FlightPriceChangedEvent e")
    List<FlightPriceChangedEvent> findAllFlightPriceChangeEvents();
}
