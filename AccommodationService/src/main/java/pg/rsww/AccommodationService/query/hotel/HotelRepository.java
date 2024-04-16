package pg.rsww.AccommodationService.query.hotel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pg.rsww.AccommodationService.query.entity.Hotel;

import java.util.List;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, String> {
    Hotel findHotelByUuid(String uuid);
    List<Hotel> findAllByCountry(String country);
}
