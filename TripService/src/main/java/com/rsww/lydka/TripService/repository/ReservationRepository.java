package com.rsww.lydka.TripService.repository;

import com.rsww.lydka.TripService.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, UUID> {
    List<Reservation> findReservationsByReservationId(String reservationId);
    List<Reservation> findAllByUser(String user);
}