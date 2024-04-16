package pg.rsww.AccommodationService.command.hotel;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.command.AddNewHotelCommand;

@Component
public class HotelCommandListener {
    private final HotelCommandService hotelCommandService;

    @Autowired
    public HotelCommandListener(HotelCommandService hotelCommandService) {
        this.hotelCommandService = hotelCommandService;
    }

    @RabbitListener(queues = "hotel-create-queue")
    public void AddNewHotelCommandHandler(AddNewHotelCommand addNewHotelCommand) {
        System.out.println("GOT ADD NEW HOTEL COMMAND");
        System.out.println(addNewHotelCommand);
        hotelCommandService.addNewHotel(addNewHotelCommand);
    }
}
