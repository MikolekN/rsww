package pg.rsww.AccommodationService.query.room;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.HotelAddedEvent;
import pg.rsww.AccommodationService.command.entity.RoomAddedEvent;
import pg.rsww.AccommodationService.query.hotel.HotelService;

@Component
public class RoomEventListener {
    private final RoomService roomService;

    @Autowired
    public RoomEventListener(RoomService roomService) {
        this.roomService = roomService;
    }

    @RabbitListener(queues = "room-created-queue")
    public void HotelAddedEventHandler(RoomAddedEvent roomAddedEvent) {
        System.out.println("GOT NEW ROOM ADDED EVENT");
        System.out.println(roomAddedEvent);
        roomService.addNewRoom(roomAddedEvent);
    }
}
