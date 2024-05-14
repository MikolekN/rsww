package pg.rsww.AccommodationService.query.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pg.rsww.AccommodationService.command.entity.RoomAddedEvent;
import pg.rsww.AccommodationService.query.entity.Hotel;
import pg.rsww.AccommodationService.query.entity.Room;
import pg.rsww.AccommodationService.query.hotel.HotelRepository;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void addNewRoom(RoomAddedEvent roomAddedEvent) {
        roomRepository.save(Room.builder()
                        .uuid(roomAddedEvent.getRoomUuid().toString())
                        .capacity(roomAddedEvent.getCapacity())
                        .type(roomAddedEvent.getType())
                        .basePrice(roomAddedEvent.getBasePrice())
                        .hotelUuid(roomAddedEvent.getHotelUuid().toString())
                .build());
    }
}
