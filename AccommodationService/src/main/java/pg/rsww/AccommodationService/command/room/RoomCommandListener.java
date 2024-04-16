package pg.rsww.AccommodationService.command.room;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.command.AddNewHotelCommand;
import pg.rsww.AccommodationService.command.entity.command.AddNewRoomCommand;

@Component
public class RoomCommandListener {
    private RoomCommandService roomCommandService;

    @Autowired
    public RoomCommandListener(RoomCommandService roomCommandService) {
        this.roomCommandService = roomCommandService;
    }

    @RabbitListener(queues = "room-create-queue")
    public void AddNewRoomCommandHandler(AddNewRoomCommand addNewRoomCommand) {
        System.out.println("GOT ADD NEW ROOM COMMAND");
        System.out.println(addNewRoomCommand);
        roomCommandService.addNewHotel(addNewRoomCommand);
    }
}
