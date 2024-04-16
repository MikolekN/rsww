package pg.rsww.AccommodationService.query.hotel;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.HotelAddedEvent;

@Component
public class HotelEventListener {
    private final HotelService hotelService;

    @Autowired
    public HotelEventListener(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @RabbitListener(queues = "hotel-created-queue")
    public void HotelAddedEventHandler(HotelAddedEvent hotelAddedEvent) {
        System.out.println("GOT NEW HOTEL ADDED EVENT");
        System.out.println(hotelAddedEvent);
        hotelService.addNewHotel(hotelAddedEvent);
    }
}
