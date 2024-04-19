package pg.rsww.AccommodationService.command.hotel;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.HotelAddedEvent;

@Component
public class HotelEventNotifier {
    private final RabbitTemplate rabbitTemplate;
    private final String hotelCreatedQueue;
    @Autowired
    public HotelEventNotifier(RabbitTemplate rabbitTemplate,
                              @Value("${spring.rabbitmq.queue.hotelCreatedQueue}") String hotelCreatedQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.hotelCreatedQueue = hotelCreatedQueue;
    }
    public void HotelAddedEventNotify(HotelAddedEvent hotelAddedEvent) {
        rabbitTemplate.convertAndSend(hotelCreatedQueue, hotelAddedEvent);
    }
}
