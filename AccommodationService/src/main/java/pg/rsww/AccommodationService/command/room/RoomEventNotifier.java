package pg.rsww.AccommodationService.command.room;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.HotelAddedEvent;
import pg.rsww.AccommodationService.command.entity.RoomAddedEvent;

@Component
public class RoomEventNotifier {
    private final RabbitTemplate rabbitTemplate;
    private final String roomCreatedQueue;
    @Autowired
    public RoomEventNotifier(RabbitTemplate rabbitTemplate,
                             @Value("${spring.rabbitmq.queue.roomCreatedQueue}") String roomCreatedQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.roomCreatedQueue = roomCreatedQueue;
    }
    public void RoomAddedEventNotify(RoomAddedEvent roomAddedEvent) {
        rabbitTemplate.convertAndSend(roomCreatedQueue, roomAddedEvent);
    }
}
