package pg.rsww.AccommodationService.query.hotel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger log = LoggerFactory.getLogger(HotelEventListener.class);
    @Autowired
    public HotelEventListener(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.hotelCreatedQueue}")
    public void HotelAddedEventHandler(HotelAddedEvent hotelAddedEvent) {
        log.info(String.format("Received HotelAddedEvent %s", hotelAddedEvent));
        hotelService.addNewHotel(hotelAddedEvent);
    }
}
