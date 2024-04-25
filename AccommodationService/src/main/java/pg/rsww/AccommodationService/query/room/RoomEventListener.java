package pg.rsww.AccommodationService.query.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.HotelAddedEvent;
import pg.rsww.AccommodationService.command.entity.RoomAddedEvent;
import pg.rsww.AccommodationService.query.hotel.HotelEventListener;
import pg.rsww.AccommodationService.query.hotel.HotelService;

@Component
public class RoomEventListener {
    private final RoomService roomService;
    private final static Logger log = LoggerFactory.getLogger(HotelEventListener.class);


    @Autowired
    public RoomEventListener(RoomService roomService) {
        this.roomService = roomService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.roomCreatedQueue}")
    public void RoomAddedEventHandler(RoomAddedEvent roomAddedEvent) {
        log.info(String.format("Received RoomAddedEvent %s", roomAddedEvent));
        roomService.addNewRoom(roomAddedEvent);
    }
}
