package pg.rsww.AccommodationService.command.hotel;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.HotelAddedEvent;
import pg.rsww.AccommodationService.command.entity.HotelRemovedEvent;

@Component
public class HotelEventNotifier {
    private final RabbitTemplate rabbitTemplate;
    private final String hotelCreatedQueue;
    private final String hotelRemovedEventQueue;
    private final String hotelRemovedEventOfferQueue;
    @Autowired
    public HotelEventNotifier(RabbitTemplate rabbitTemplate,
                              @Value("${spring.rabbitmq.queue.hotelCreatedQueue}") String hotelCreatedQueue,
                              @Value("${spring.rabbitmq.queue.HotelRemovedEventQueue}") String hotelRemovedEventQueue,
                              @Value("${spring.rabbitmq.queue.HotelRemovedEventOfferQueue}") String hotelRemovedEventOfferQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.hotelCreatedQueue = hotelCreatedQueue;
        this.hotelRemovedEventQueue = hotelRemovedEventQueue;
        this.hotelRemovedEventOfferQueue = hotelRemovedEventOfferQueue;
    }
    public void HotelAddedEventNotify(HotelAddedEvent hotelAddedEvent) {
        rabbitTemplate.convertAndSend(hotelCreatedQueue, hotelAddedEvent);
    }
    public void HotelRemovedEventNotify(HotelRemovedEvent hotelRemovedEvent) {
        rabbitTemplate.convertAndSend(hotelRemovedEventQueue, hotelRemovedEvent);
        rabbitTemplate.convertAndSend(hotelRemovedEventOfferQueue, hotelRemovedEvent);
    }
}
