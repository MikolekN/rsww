package pg.rsww.AccommodationService.command.hotel;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pg.rsww.AccommodationService.command.entity.Event;
import pg.rsww.AccommodationService.command.entity.HotelAddedEvent;
import pg.rsww.AccommodationService.command.entity.HotelEvent;
import pg.rsww.AccommodationService.command.entity.HotelRemovedEvent;
import pg.rsww.AccommodationService.command.entity.command.AddNewHotelCommand;
import pg.rsww.AccommodationService.command.entity.command.GetLastHotelChangesRequest;
import pg.rsww.AccommodationService.command.entity.command.RemoveHotelCommand;

import java.util.Comparator;
import java.util.List;
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
                addNewHotelCommand.getCountry(),
                addNewHotelCommand.getStars());
        hotelEventRepository.save(hotelAddedEvent);
        return hotelAddedEvent;
        //System.out.println(hotelEventRepository.findAll());
        //rabbitTemplate.convertAndSend("hotel-created-queue", hotelAddedEvent);
    }

    public HotelRemovedEvent removeHotel(RemoveHotelCommand removeHotelCommand) {
        String country ="";
        String hotelName="";
        List<HotelEvent> hotelEventList = hotelEventRepository.findAllByHotelUuid(removeHotelCommand.getUuid());
        for (HotelEvent event: hotelEventList) {
            if (event instanceof HotelAddedEvent) {
                country = ((HotelAddedEvent) event).getCountry();
                hotelName = ((HotelAddedEvent) event).getName();
            }
        }
        HotelRemovedEvent hotelRemovedEvent = new HotelRemovedEvent(UUID.randomUUID(), removeHotelCommand.getUuid(), country, hotelName);
        hotelEventRepository.save(hotelRemovedEvent);
        return hotelRemovedEvent;
    }

    public List<HotelRemovedEvent> getLastChangeEvents(GetLastHotelChangesRequest request) {
        return hotelEventRepository.findAllBy().stream()
                .sorted(Comparator.comparing(Event::getTimeStamp).reversed()) // maybe .reversed()
                .limit(10)
                .toList();
    }
}
