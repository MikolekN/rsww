package pg.rsww.AccommodationService.command.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pg.rsww.AccommodationService.command.entity.RoomEvent;
import pg.rsww.AccommodationService.command.entity.RoomPriceChangeEvent;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomPriceChangeEventRepository extends JpaRepository<RoomPriceChangeEvent, UUID> {
}