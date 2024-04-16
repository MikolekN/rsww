package pg.rsww.AccommodationService.query.reservation;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.ReservationCancelledEvent;
import pg.rsww.AccommodationService.command.entity.ReservationMadeEvent;
import pg.rsww.AccommodationService.command.entity.RoomAddedEvent;
import pg.rsww.AccommodationService.query.room.RoomService;

@Component
public class ReservationEventListener {
    private final ReservationService reservationService;

    @Autowired
    public ReservationEventListener(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    @RabbitListener(queues = "reservation-made-queue")
    public void ReservationMadeEventHandler(ReservationMadeEvent reservationMadeEvent) {
        System.out.println("GOT NEW RESERVATION MADE EVENT");
        System.out.println(reservationMadeEvent);
        reservationService.makeNewReservation(reservationMadeEvent);
    }
    @RabbitListener(queues = "reservation-cancelled-queue")
    public void ReservationCancelledEventHandler(ReservationCancelledEvent reservationCancelledEvent) {
        System.out.println("GOT NEW RESERVATION CANCELLED EVENT");
        System.out.println(reservationCancelledEvent);
        reservationService.cancelReservation(reservationCancelledEvent);
    }
}
