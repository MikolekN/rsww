package pg.rsww.AccommodationService.command.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pg.rsww.AccommodationService.command.entity.HotelEvent;
import pg.rsww.AccommodationService.command.entity.RoomAddedEvent;
import pg.rsww.AccommodationService.command.entity.RoomEvent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomEventRepository extends  JpaRepository<RoomEvent, UUID> {
    List<RoomEvent> findAllByHotelUuid(UUID hotelUuid);
    Optional<RoomAddedEvent> findRoomEventByRoomUuid(UUID id);
}