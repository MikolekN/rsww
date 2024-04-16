package pg.rsww.AccommodationService.command.hotel;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pg.rsww.AccommodationService.command.entity.HotelAddedEvent;
import pg.rsww.AccommodationService.command.entity.command.AddNewHotelCommand;

import java.util.UUID;

@Service
public class HotelCommandService {
    private final HotelEventRepository hotelEventRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public HotelCommandService(HotelEventRepository hotelEventRepository,
                               RabbitTemplate rabbitTemplate) {
        this.hotelEventRepository = hotelEventRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void addNewHotel(AddNewHotelCommand addNewHotelCommand) {
        HotelAddedEvent hotelAddedEvent = new HotelAddedEvent(
                UUID.randomUUID(),
                addNewHotelCommand.getUuid(),
                addNewHotelCommand.getName(),
                addNewHotelCommand.getCountry());
        hotelEventRepository.save(hotelAddedEvent);
        System.out.println(hotelEventRepository.findAll());
        rabbitTemplate.convertAndSend("hotel-created-queue", hotelAddedEvent);
    }
}
