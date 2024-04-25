package pg.rsww.AccommodationService.query.room;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pg.rsww.AccommodationService.query.entity.Room;

import java.util.List;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {
    List<Room> findAllByHotelUuid(String hotelUuid);
    List<Room> findAllByHotelUuidAndNumberOfAdultsAndNumberOfChildren(String hotelUuid, int numberOfAdults, int numberOfChildren);
}
