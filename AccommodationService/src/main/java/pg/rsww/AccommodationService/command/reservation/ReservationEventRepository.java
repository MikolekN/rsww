package pg.rsww.AccommodationService.command.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pg.rsww.AccommodationService.command.entity.ReservationEvent;

import java.util.UUID;

@Repository
public interface ReservationEventRepository extends JpaRepository<ReservationEvent, UUID> {

}
