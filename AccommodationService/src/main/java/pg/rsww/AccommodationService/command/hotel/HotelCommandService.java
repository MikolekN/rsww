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

    @Autowired
    public HotelCommandService(HotelEventRepository hotelEventRepository) {
        this.hotelEventRepository = hotelEventRepository;
    }

    public HotelAddedEvent addNewHotel(AddNewHotelCommand addNewHotelCommand) {
        HotelAddedEvent hotelAddedEvent = new HotelAddedEvent(
                UUID.randomUUID(),
                addNewHotelCommand.getUuid(),
                addNewHotelCommand.getName(),
                addNewHotelCommand.getCountry());
        hotelEventRepository.save(hotelAddedEvent);
        return hotelAddedEvent;
        //System.out.println(hotelEventRepository.findAll());
        //rabbitTemplate.convertAndSend("hotel-created-queue", hotelAddedEvent);
    }
}
