package pg.rsww.AccommodationService.command.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pg.rsww.AccommodationService.command.entity.Event;
import pg.rsww.AccommodationService.command.entity.HotelEvent;

import java.util.List;
import java.util.UUID;

@Repository
public interface HotelEventRepository extends JpaRepository<HotelEvent, UUID> {
    List<HotelEvent> findAllByHotelUuid(UUID hotelUuid);
}
