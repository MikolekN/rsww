package pg.rsww.AccommodationService.command.room;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.HotelAddedEvent;
import pg.rsww.AccommodationService.command.entity.RoomAddedEvent;
import pg.rsww.AccommodationService.command.entity.RoomPriceChangeEvent;

@Component
public class RoomEventNotifier {
    private final RabbitTemplate rabbitTemplate;
    private final String roomCreatedQueue;
    private final String roomPriceChangedEventQueue;
    private final String roomPriceChangedEventOfferQueue;
    @Autowired
    public RoomEventNotifier(RabbitTemplate rabbitTemplate,
                             @Value("${spring.rabbitmq.queue.roomCreatedQueue}") String roomCreatedQueue,
                             @Value("${spring.rabbitmq.queue.RoomPriceChangedEventQueue}") String roomPriceChangedEventQueue,
                             @Value("${spring.rabbitmq.queue.RoomPriceChangedEventOfferQueue}") String roomPriceChangedEventOfferQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.roomCreatedQueue = roomCreatedQueue;
        this.roomPriceChangedEventQueue = roomPriceChangedEventQueue;
        this.roomPriceChangedEventOfferQueue = roomPriceChangedEventOfferQueue;
    }
    public void RoomAddedEventNotify(RoomAddedEvent roomAddedEvent) {
        rabbitTemplate.convertAndSend(roomCreatedQueue, roomAddedEvent);
    }

    public void RoomPriceChangeEventNotify(RoomPriceChangeEvent roomPriceChangeEvent) {
        rabbitTemplate.convertAndSend(roomPriceChangedEventQueue, roomPriceChangeEvent);
        rabbitTemplate.convertAndSend(roomPriceChangedEventOfferQueue, roomPriceChangeEvent);
    }
}
