package com.rsww.lydka.TripService.repository;

import com.rsww.lydka.TripService.entity.ReservationInfo;
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
public interface ReservationInfoRepository extends MongoRepository<ReservationInfo, UUID> {
    List<ReservationInfo> findReservationsByReservationId(String reservationId);
    List<ReservationInfo> findAllByUser(String user);
}
