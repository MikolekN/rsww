package pg.rsww.AccommodationService.query.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pg.rsww.AccommodationService.command.entity.RoomAddedEvent;
import pg.rsww.AccommodationService.query.changequery.GetRoomsRequest;
import pg.rsww.AccommodationService.query.changequery.GetRoomsResponse;
import pg.rsww.AccommodationService.query.changequery.RoomType;
import pg.rsww.AccommodationService.query.entity.Hotel;
import pg.rsww.AccommodationService.query.entity.Room;
import pg.rsww.AccommodationService.query.event.GetHotelInfoResponse;
import pg.rsww.AccommodationService.query.hotel.HotelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

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

    public GetRoomsResponse getRooms(GetRoomsRequest getRoomsRequest) {
        List<Room> roomList = roomRepository.findAllByHotelUuid(getRoomsRequest.getHotelUuid());
        List<String> roomTypeNamesList = roomList.stream().map(Room::getType).distinct().sorted().collect(Collectors.toList());

        List<RoomType> roomTypeList = new ArrayList<>();
        for (String roomTypeName: roomTypeNamesList) {
            for (Room room: roomList) {
                if (room.getType().equals(roomTypeName)) {
                    roomTypeList.add(RoomType.builder()
                            .type(roomTypeName)
                            .basePrice(room.getBasePrice())
                            .build());
                    break;
                }
            }
        }
        return new GetRoomsResponse(roomTypeList);
    }
}
