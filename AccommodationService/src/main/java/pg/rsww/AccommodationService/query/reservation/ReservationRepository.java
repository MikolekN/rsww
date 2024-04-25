package pg.rsww.AccommodationService.query.reservation;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pg.rsww.AccommodationService.query.entity.Reservation;
import pg.rsww.AccommodationService.query.entity.Room;

import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {
    Reservation findByUuid(String uuid);

    List<Reservation> findAllByHotelUuidAndRoomUuid(String hotelUuid, String roomUuid);

}
