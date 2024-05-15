package com.example.transportation.flight.repo;

import com.example.transportation.flight.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {
    @Query("SELECT f FROM Flight f WHERE DATE(f.departureDate) = DATE(:date)")
    List<Flight> findAllByDepartureDateIgnoringTime(@Param("date") Date date);

    Flight findByPrice(int price);
}
