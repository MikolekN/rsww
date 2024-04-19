package pg.rsww.AccommodationService.query.reservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.ReservationCancelledEvent;
import pg.rsww.AccommodationService.command.entity.ReservationEvent;
import pg.rsww.AccommodationService.command.entity.ReservationMadeEvent;
import pg.rsww.AccommodationService.command.entity.RoomAddedEvent;
import pg.rsww.AccommodationService.query.hotel.HotelEventListener;
import pg.rsww.AccommodationService.query.room.RoomService;

@Component
public class ReservationEventListener {
    private final ReservationService reservationService;
    private final static Logger log = LoggerFactory.getLogger(ReservationEvent.class);


    @Autowired
    public ReservationEventListener(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.reservationMadeQueue}")
    public void ReservationMadeEventHandler(ReservationMadeEvent reservationMadeEvent) {
        log.info(String.format("Received ReservationMadeEvent %s", reservationMadeEvent));
        reservationService.makeNewReservation(reservationMadeEvent);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.reservationCancelledQueue}")
    public void ReservationCancelledEventHandler(ReservationCancelledEvent reservationCancelledEvent) {
        log.info(String.format("Received ReservationCancelledEvent %s", reservationCancelledEvent));
        reservationService.cancelReservation(reservationCancelledEvent);
    }
}
