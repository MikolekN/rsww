package pg.rsww.AccommodationService.command.hotel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.HotelAddedEvent;
import pg.rsww.AccommodationService.command.entity.HotelRemovedEvent;
import pg.rsww.AccommodationService.command.entity.command.AddNewHotelCommand;
import pg.rsww.AccommodationService.command.entity.command.GetLastHotelChangesRequest;
import pg.rsww.AccommodationService.command.entity.command.GetLastRoomChangesRequest;
import pg.rsww.AccommodationService.command.entity.command.RemoveHotelCommand;
import pg.rsww.AccommodationService.command.entity.response.GetLastHotelChangesResponse;
import pg.rsww.AccommodationService.command.entity.response.GetLastRoomChangesResponse;
import pg.rsww.AccommodationService.query.hotel.HotelEventListener;

import java.util.Comparator;
import java.util.List;

@Component
public class HotelCommandListener {
    private final HotelCommandService hotelCommandService;
    private final HotelEventNotifier hotelEventNotifier;

    private final static Logger log = LoggerFactory.getLogger(HotelCommandListener.class);


    @Autowired
    public HotelCommandListener(HotelCommandService hotelCommandService, HotelEventNotifier hotelEventNotifier) {
        this.hotelCommandService = hotelCommandService;
        this.hotelEventNotifier = hotelEventNotifier;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.hotelCreateQueue}")
    public void AddNewHotelCommandHandler(AddNewHotelCommand addNewHotelCommand) {
        log.info(String.format("Received AddNewHotelCommand %s", addNewHotelCommand));
        HotelAddedEvent event = hotelCommandService.addNewHotel(addNewHotelCommand);
        hotelEventNotifier.HotelAddedEventNotify(event);
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.RemoveHotelQueue}")
    public void removeHotelCommandHandler(RemoveHotelCommand removeHotelCommand) {
        log.info(String.format("Received RemoveHotelCommand %s", removeHotelCommand));
        HotelRemovedEvent event = hotelCommandService.removeHotel(removeHotelCommand);
        hotelEventNotifier.HotelRemovedEventNotify(event);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.GetHotelChangeEventsQueue}")
    public GetLastHotelChangesResponse getLastFlightChanges(GetLastHotelChangesRequest request) {
        List<HotelRemovedEvent> hotelRemovedEvents = hotelCommandService.getLastChangeEvents(request);
        return new GetLastHotelChangesResponse(hotelRemovedEvents);
    }
}
