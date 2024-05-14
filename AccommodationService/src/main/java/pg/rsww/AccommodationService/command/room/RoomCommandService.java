package pg.rsww.AccommodationService.command.room;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pg.rsww.AccommodationService.command.entity.RoomAddedEvent;
import pg.rsww.AccommodationService.command.entity.command.AddNewRoomCommand;

import java.util.UUID;

@Service
public class RoomCommandService {
    private final RoomEventRepository roomEventRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RoomCommandService(RoomEventRepository roomEventRepository, RabbitTemplate rabbitTemplate) {
        this.roomEventRepository = roomEventRepository;
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
}
