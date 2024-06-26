package pg.rsww.AccommodationService.query.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.HotelAddedEvent;
import pg.rsww.AccommodationService.command.entity.RoomAddedEvent;
import pg.rsww.AccommodationService.command.entity.RoomPriceChangeEvent;
import pg.rsww.AccommodationService.query.changequery.GetHotelsRequest;
import pg.rsww.AccommodationService.query.changequery.GetHotelsResponse;
import pg.rsww.AccommodationService.query.changequery.GetRoomsRequest;
import pg.rsww.AccommodationService.query.changequery.GetRoomsResponse;
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
    @RabbitListener(queues = "${spring.rabbitmq.queue.GetAllRoomTypesQueue}")
    public GetRoomsResponse GetAllRoomTypesHandler(GetRoomsRequest getRoomsRequest) {
        return roomService.getRooms(getRoomsRequest);
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.RoomPriceChangedEventQueue}")
    public void RoomPriceChangeEventHandler(RoomPriceChangeEvent roomPriceChangeEvent) {
        log.info(String.format("Received RoomPriceChangeEvent %s", roomPriceChangeEvent));
        roomService.changeRoomPrice(roomPriceChangeEvent);
    }
}
