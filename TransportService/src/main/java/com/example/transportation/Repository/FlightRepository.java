package com.example.transportation.Repository;

import com.example.transportation.Entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {
    List<Flight> findAllByDepartureDate(String date);
}
