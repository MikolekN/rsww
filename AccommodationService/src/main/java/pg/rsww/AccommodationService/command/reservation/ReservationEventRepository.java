package pg.rsww.AccommodationService.command.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pg.rsww.AccommodationService.command.entity.ReservationEvent;
import pg.rsww.AccommodationService.command.entity.ReservationMadeEvent;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationEventRepository extends JpaRepository<ReservationEvent, UUID> {
    @Query("SELECT rme FROM ReservationMadeEvent rme WHERE TYPE(rme) = ReservationMadeEvent AND rme.room = :room")
    List<ReservationMadeEvent> findAllByRoom(@Param("room") UUID room);

    List<ReservationEvent> findAllByReservationUuid(UUID reservationUUID);
}
