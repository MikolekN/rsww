package pg.rsww.AccommodationService.command.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.HotelRemovedEvent;
import pg.rsww.AccommodationService.command.entity.RoomAddedEvent;
import pg.rsww.AccommodationService.command.entity.RoomPriceChangeEvent;
import pg.rsww.AccommodationService.command.entity.command.*;
import pg.rsww.AccommodationService.command.entity.response.GetLastHotelChangesResponse;
import pg.rsww.AccommodationService.command.entity.response.GetLastRoomChangesResponse;
import pg.rsww.AccommodationService.query.hotel.HotelEventListener;

import java.util.List;

@Component
public class RoomCommandListener {
    private final RoomCommandService roomCommandService;
    private final RoomEventNotifier roomEventNotifier;
    private final static Logger log = LoggerFactory.getLogger(RoomCommandListener.class);

    @Autowired
    public RoomCommandListener(RoomCommandService roomCommandService, RoomEventNotifier roomEventNotifier) {
        this.roomCommandService = roomCommandService;
        this.roomEventNotifier = roomEventNotifier;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.roomCreateQueue}")
    public void AddNewRoomCommandHandler(AddNewRoomCommand addNewRoomCommand) {
        log.info(String.format("Received AddNewRoomCommand %s", addNewRoomCommand));
        RoomAddedEvent event = roomCommandService.addNewRoom(addNewRoomCommand);
        roomEventNotifier.RoomAddedEventNotify(event);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.ChangeRoomPriceQueue}")
    public void ChangeRoomPriceCommandHandler(ChangeRoomPriceCommand changeRoomPriceCommand) {
        log.info(String.format("Received ChangeRoomPriceCommand %s", changeRoomPriceCommand));
        RoomPriceChangeEvent event = roomCommandService.changeRoomPrice(changeRoomPriceCommand);
        roomEventNotifier.RoomPriceChangeEventNotify(event);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.GetRoomChangeEventsQueue}")
    public GetLastRoomChangesResponse getLastFlightChanges(GetLastRoomChangesRequest request) {
        List<RoomPriceChangeEvent> roomPriceChangeEvents = roomCommandService.getLastChangeEvents(request);
        return new GetLastRoomChangesResponse(roomPriceChangeEvents);
    }
}
