package pg.rsww.AccommodationService.command.hotel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.HotelAddedEvent;
import pg.rsww.AccommodationService.command.entity.command.AddNewHotelCommand;
import pg.rsww.AccommodationService.query.hotel.HotelEventListener;

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
}
