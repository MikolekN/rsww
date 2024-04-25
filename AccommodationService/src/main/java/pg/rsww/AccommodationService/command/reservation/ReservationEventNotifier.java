package pg.rsww.AccommodationService.command.reservation;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.ReservationCancelledEvent;
import pg.rsww.AccommodationService.command.entity.ReservationMadeEvent;
import pg.rsww.AccommodationService.command.entity.RoomAddedEvent;

@Component
public class ReservationEventNotifier {
    private final RabbitTemplate rabbitTemplate;
    private final String reservationMadeQueue;
    private final String reservationCancelledQueue;
    // TODO maybe instead of queues - use topic and bind two queues to it, so the Event
    // TODO goes to both QueryService and as a response to OrderService
    // TODO
    @Autowired
    public ReservationEventNotifier(RabbitTemplate rabbitTemplate,
                                    @Value("${spring.rabbitmq.queue.reservationMadeQueue}") String reservationMadeQueue,
                                    @Value("${spring.rabbitmq.queue.reservationCancelledQueue}") String reservationCancelledQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.reservationMadeQueue = reservationMadeQueue;
        this.reservationCancelledQueue = reservationCancelledQueue;
    }
    public void ReservationMadeEventNotify(ReservationMadeEvent reservationMadeEvent) {
        rabbitTemplate.convertAndSend(reservationMadeQueue, reservationMadeEvent);
    }
    public void ReservationCancelledEventNotify(ReservationCancelledEvent reservationCancelledEvent) {
        rabbitTemplate.convertAndSend(reservationCancelledQueue, reservationCancelledEvent);
    }
}
