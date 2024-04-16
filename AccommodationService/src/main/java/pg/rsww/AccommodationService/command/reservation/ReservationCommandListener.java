package pg.rsww.AccommodationService.command.reservation;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pg.rsww.AccommodationService.command.entity.command.AddNewHotelCommand;
import pg.rsww.AccommodationService.command.entity.command.CancelReservationCommand;
import pg.rsww.AccommodationService.command.entity.command.MakeNewReservationCommand;
import pg.rsww.AccommodationService.command.hotel.HotelCommandService;

@Component
public class ReservationCommandListener {
    private final ReservationCommandService reservationCommandService;

    @Autowired
    public ReservationCommandListener(ReservationCommandService reservationCommandService) {
        this.reservationCommandService = reservationCommandService;
    }

    @RabbitListener(queues = "reservation-make-queue")
    public void MakeNewReservationCommandHandler(MakeNewReservationCommand makeNewReservationCommand) {
        System.out.println("GOT MAKE NEW RESERVATION COMMAND");
        System.out.println(makeNewReservationCommand);
        reservationCommandService.makeNewReservation(makeNewReservationCommand);
    }
    @RabbitListener(queues = "reservation-cancel-queue")
    public void CancelReservationCommandHandler(CancelReservationCommand cancelReservationCommand) {
        System.out.println("GOT MAKE CANCEL RESERVATION COMMAND");
        System.out.println(cancelReservationCommand);
        reservationCommandService.cancelReservation(cancelReservationCommand);
    }
}
