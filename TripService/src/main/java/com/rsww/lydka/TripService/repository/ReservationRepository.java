package com.rsww.lydka.TripService.repository;

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
public interface ReservationRepository extends MongoRepository<ReservationRepository.Reservation, UUID> {
    List<Reservation> findReservationsByReservationId(String reservationId);
    List<Reservation> findAllByUserId(Long userId);
    @Data
    @Builder
    @Jacksonized
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Reservation {
        private String reservationId;
        private String startFlightReservation;
        private String endFlightReservation;
        private String startFlightId;
        private String endFlightId;
        private Long userId;
        private String hotelReservation;
        private String tripId;
        private String hotelId;
        private Boolean payed;
        private LocalDateTime reserved;
        private Double price;
    }
}