package pg.rsww.AccommodationService.command.reservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.ReservationCancelledEvent;
import pg.rsww.AccommodationService.command.entity.ReservationMadeEvent;
import pg.rsww.AccommodationService.command.entity.command.AddNewHotelCommand;
import pg.rsww.AccommodationService.command.entity.command.CancelReservationCommand;
import pg.rsww.AccommodationService.command.entity.command.MakeNewReservationCommand;
import pg.rsww.AccommodationService.command.hotel.HotelCommandService;
import pg.rsww.AccommodationService.query.hotel.HotelEventListener;

@Component
public class ReservationCommandListener {
    private final ReservationCommandService reservationCommandService;
    private final ReservationEventNotifier reservationEventNotifier;
    private final static Logger log = LoggerFactory.getLogger(ReservationCommandListener.class);

    @Autowired
    public ReservationCommandListener(ReservationCommandService reservationCommandService, ReservationEventNotifier reservationEventNotifier) {
        this.reservationCommandService = reservationCommandService;
        this.reservationEventNotifier = reservationEventNotifier;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.reservationMakeQueue}")
    public void MakeNewReservationCommandHandler(MakeNewReservationCommand makeNewReservationCommand) {
        log.info(String.format("Received MakeNewReservationCommand %s", makeNewReservationCommand));
        ReservationMadeEvent event = reservationCommandService.makeNewReservation(makeNewReservationCommand);
        reservationEventNotifier.ReservationMadeEventNotify(event);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.reservationCancelQueue}")
    public void CancelReservationCommandHandler(CancelReservationCommand cancelReservationCommand) {
        log.info(String.format("Received CancelReservationCommand %s", cancelReservationCommand));
        ReservationCancelledEvent event =reservationCommandService.cancelReservation(cancelReservationCommand);
        reservationEventNotifier.ReservationCancelledEventNotify(event);
    }
}