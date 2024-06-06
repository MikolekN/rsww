package pg.rsww.AccommodationService.command.room;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pg.rsww.AccommodationService.command.entity.RoomAddedEvent;
import pg.rsww.AccommodationService.command.entity.RoomEvent;
import pg.rsww.AccommodationService.command.entity.RoomPriceChangeEvent;
import pg.rsww.AccommodationService.command.entity.command.AddNewRoomCommand;
import pg.rsww.AccommodationService.command.entity.command.ChangeRoomPriceCommand;

import java.util.List;
import java.util.UUID;

@Service
public class RoomCommandService {
    private final RoomEventRepository roomEventRepository;
    private final RoomPriceChangeEventRepository roomPriceChangeEventRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RoomCommandService(RoomEventRepository roomEventRepository, RoomPriceChangeEventRepository roomPriceChangeEventRepository, RabbitTemplate rabbitTemplate) {
        this.roomEventRepository = roomEventRepository;
        this.roomPriceChangeEventRepository = roomPriceChangeEventRepository;
        this.rabbitTemplate = rabbitTemplate;
    }
    public RoomAddedEvent addNewRoom(AddNewRoomCommand addNewRoomCommand) {
        RoomAddedEvent roomAddedEvent = new RoomAddedEvent(UUID.randomUUID(),
                addNewRoomCommand.getUuid(),
                addNewRoomCommand.getCapacity(),
                addNewRoomCommand.getType(),
                addNewRoomCommand.getBasePrice(),
                addNewRoomCommand.getHotelUuid());
        roomEventRepository.save(roomAddedEvent);
        //System.out.println(roomEventRepository.findAll());
        //rabbitTemplate.convertAndSend("room-created-queue", roomAddedEvent);
        return roomAddedEvent;
    }

    public RoomPriceChangeEvent changeRoomPrice(ChangeRoomPriceCommand changeRoomPriceCommand) {
        List<RoomEvent> roomAddedEventList = roomEventRepository.findAllByHotelUuid(changeRoomPriceCommand.getHotelUuid());
        float oldPrice = 0.0f;
        for (RoomEvent roomEvent: roomAddedEventList) {
            if (roomEvent instanceof RoomAddedEvent) {
                RoomAddedEvent roomAddedEvent = (RoomAddedEvent) roomEvent;
                oldPrice = roomAddedEvent.getBasePrice();
                break;
            }
        }
        RoomPriceChangeEvent event = new RoomPriceChangeEvent(UUID.randomUUID(),
                changeRoomPriceCommand.getHotelUuid(),
                changeRoomPriceCommand.getRoomType(),
                oldPrice,
                changeRoomPriceCommand.getChangedPrice());
        roomPriceChangeEventRepository.save(event);
        return event;
    }
}
