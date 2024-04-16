package pg.rsww.AccommodationService.command.reservation;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pg.rsww.AccommodationService.command.entity.HotelAddedEvent;
import pg.rsww.AccommodationService.command.entity.ReservationCancelledEvent;
import pg.rsww.AccommodationService.command.entity.ReservationMadeEvent;
import pg.rsww.AccommodationService.command.entity.command.AddNewHotelCommand;
import pg.rsww.AccommodationService.command.entity.command.CancelReservationCommand;
import pg.rsww.AccommodationService.command.entity.command.MakeNewReservationCommand;
import pg.rsww.AccommodationService.command.hotel.HotelEventRepository;

import java.util.UUID;

@Service
public class ReservationCommandService {
    private final ReservationEventRepository reservationEventRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ReservationCommandService(ReservationEventRepository reservationEventRepository,
                               RabbitTemplate rabbitTemplate) {
        this.reservationEventRepository = reservationEventRepository;
        this.rabbitTemplate = rabbitTemplate;
    }
    public void makeNewReservation(MakeNewReservationCommand makeNewReservationCommand) {
        UUID hotelUuid = makeNewReservationCommand.getHotel();
        UUID roomUuid = UUID.randomUUID(); // FIXME GET REAL IDS OF HOTEL AND ROOM
        // FIXME GET REAL IDS OF HOTEL AND ROOM
        // TODO - LOGIC OF MAKING RESERVATION - CHECKING WHICH ROOM FROM HOTEL IS GOOD BASED ON CHOSEN TYPE
        // TODO - find if any room is free based on Hotel, RoomType,
        // TODO - and StartDate, EndDate and list of Uncancelled reservations
        ReservationMadeEvent reservationMadeEvent = new ReservationMadeEvent(UUID.randomUUID(),
                makeNewReservationCommand.getUuid(), makeNewReservationCommand.getTimeStamp(),
                makeNewReservationCommand.getStartDate(), makeNewReservationCommand.getEndDate(),
                makeNewReservationCommand.getNumberOfAdults(), makeNewReservationCommand.getNumberOfChildren(), hotelUuid, roomUuid);
        reservationEventRepository.save(reservationMadeEvent);
        System.out.println(reservationEventRepository.findAll());
        rabbitTemplate.convertAndSend("reservation-made-queue", reservationMadeEvent);
    }
    public void cancelReservation(CancelReservationCommand cancelReservationCommand) {
        ReservationCancelledEvent reservationCancelledEvent = new ReservationCancelledEvent(UUID.randomUUID(), cancelReservationCommand.getUuid());
        reservationEventRepository.save(reservationCancelledEvent);
        System.out.println(reservationEventRepository.findAll());
        rabbitTemplate.convertAndSend("reservation-cancelled-queue", reservationCancelledEvent);
    }
}
