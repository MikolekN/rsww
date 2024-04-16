package pg.rsww.AccommodationService.command.room;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pg.rsww.AccommodationService.command.entity.HotelAddedEvent;
import pg.rsww.AccommodationService.command.entity.RoomAddedEvent;
import pg.rsww.AccommodationService.command.entity.command.AddNewHotelCommand;
import pg.rsww.AccommodationService.command.entity.command.AddNewRoomCommand;
import pg.rsww.AccommodationService.command.hotel.HotelEventRepository;

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
    public void addNewHotel(AddNewRoomCommand addNewRoomCommand) {
        RoomAddedEvent roomAddedEvent = new RoomAddedEvent(UUID.randomUUID(),
                addNewRoomCommand.getUuid(),
                addNewRoomCommand.getNumberOfAdults(),
                addNewRoomCommand.getNumberOfChildren(),
                addNewRoomCommand.getType(),
                addNewRoomCommand.getPrice(),
                addNewRoomCommand.getHotelUuid());
        roomEventRepository.save(roomAddedEvent);
        System.out.println(roomEventRepository.findAll());
        rabbitTemplate.convertAndSend("room-created-queue", roomAddedEvent);
    }
}
