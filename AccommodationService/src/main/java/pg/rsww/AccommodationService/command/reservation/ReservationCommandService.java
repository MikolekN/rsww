package pg.rsww.AccommodationService.command.reservation;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pg.rsww.AccommodationService.command.entity.*;
import pg.rsww.AccommodationService.command.entity.command.CancelReservationCommand;
import pg.rsww.AccommodationService.command.entity.command.MakeNewReservationCommand;
import pg.rsww.AccommodationService.command.hotel.HotelEventRepository;
import pg.rsww.AccommodationService.command.room.RoomEventRepository;
import pg.rsww.AccommodationService.command.room.RoomPriceChangeEventRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationCommandService {
    private final ReservationEventRepository reservationEventRepository;
    private final HotelEventRepository hotelEventRepository;
    private final RoomEventRepository roomEventRepository;
    private final RoomPriceChangeEventRepository roomPriceChangeEventRepository;

    @Autowired
    public ReservationCommandService(ReservationEventRepository reservationEventRepository, HotelEventRepository hotelEventRepository, RoomEventRepository roomEventRepository, RoomPriceChangeEventRepository roomPriceChangeEventRepository) {
        this.reservationEventRepository = reservationEventRepository;
        this.hotelEventRepository = hotelEventRepository;
        this.roomEventRepository = roomEventRepository;
        this.roomPriceChangeEventRepository = roomPriceChangeEventRepository;
    }
    public Optional<ReservationMadeEvent> makeNewReservation(MakeNewReservationCommand makeNewReservationCommand) {
        UUID hotelUuid = makeNewReservationCommand.getHotel();
        // Checking if hotel with uuid from request exists
        List<HotelEvent> hotelEvents = hotelEventRepository.findAllByHotelUuid(hotelUuid);
        if (hotelEvents.isEmpty()) {
            //System.out.println("WRONG HOTEL");
            return Optional.empty();
        }
        for (HotelEvent hotelEvent: hotelEvents) {
            if (hotelEvent instanceof HotelRemovedEvent) {
                return Optional.empty();
            }
        }
        // Checking if any of the rooms is suitable
        List<RoomEvent> roomEvents = roomEventRepository.findAllByHotelUuid(hotelUuid);
        UUID suitableRoomUuid = null;
        float roomPrice = 0;
        if (roomEvents.isEmpty()) {
            //System.out.println("NO ROOMS");
            return Optional.empty();
        }
        for (RoomEvent roomEvent: roomEvents) {
            //System.out.println(roomEvent);
            UUID roomUuid = roomEvent.getRoomUuid();
            if (roomEvent instanceof RoomAddedEvent) {
                if (((RoomAddedEvent) roomEvent).getCapacity()
                        == (makeNewReservationCommand.getNumberOfAdults() + makeNewReservationCommand.getNumberOfChildrenUnder10() + makeNewReservationCommand.getNumberOfChildrenUnder18())
                        && Objects.equals(((RoomAddedEvent) roomEvent).getType(), makeNewReservationCommand.getRoomType())) {
                    // checking if the suitable room is not reserved already
                    List<ReservationMadeEvent> reservationEvents = reservationEventRepository.findAllByRoom(roomUuid);
                    if (reservationEvents.isEmpty()) {
                        suitableRoomUuid = roomUuid;
                        break;
                    }
                    boolean suitable = true;
                    // checking if any of previous reservations overlaps with this reservation
                    for (ReservationMadeEvent reservationMadeEvent: reservationEvents) {
                        if ((!(makeNewReservationCommand.getStartDate().isBefore(reservationMadeEvent.getStartDate()) || makeNewReservationCommand.getStartDate().isAfter(reservationMadeEvent.getEndDate())))
                                || (!(makeNewReservationCommand.getEndDate().isBefore(reservationMadeEvent.getStartDate()) || makeNewReservationCommand.getEndDate().isAfter(reservationMadeEvent.getEndDate())))
                                || (!(reservationMadeEvent.getStartDate().isBefore(makeNewReservationCommand.getStartDate()) || reservationMadeEvent.getStartDate().isAfter(makeNewReservationCommand.getEndDate())))
                                || (!(reservationMadeEvent.getEndDate().isBefore(makeNewReservationCommand.getStartDate()) || reservationMadeEvent.getEndDate().isAfter(makeNewReservationCommand.getEndDate())))) {
                            // checking if overlapping reservation was cancelled
                            List<ReservationEvent> overlappingReservationEvents = reservationEventRepository.findAllByReservationUuid(reservationMadeEvent.getReservationUuid());
                            boolean isCancelled = false;
                            for (ReservationEvent event: overlappingReservationEvents) {
                                if (event instanceof ReservationCancelledEvent) {
                                    isCancelled = true;
                                    break;
                                }
                            }
                            if (!isCancelled) {
                                suitable = false;
                            }
                            break;
                        }
                    }
                    // room is suitable despite other reservations that don't overlap this one
                    if (suitable)
                    {
                        suitableRoomUuid = roomUuid;
                        roomPrice = ((RoomAddedEvent) roomEvent).getBasePrice();
                    }
                }
            }
        }

        if (suitableRoomUuid == null) {
            return Optional.empty();
        }

        // TODO: usunąc jesli nie poprawi
        roomPrice = roomEventRepository.findRoomEventByRoomUuid(suitableRoomUuid).get().getBasePrice();

        List<RoomPriceChangeEvent> roomPriceChangeEvents = roomPriceChangeEventRepository.findAllByHotelUuid(hotelUuid);
        for (RoomPriceChangeEvent roomPriceChangeEvent: roomPriceChangeEvents) {
            if (makeNewReservationCommand.getRoomType().equals(roomPriceChangeEvent.getRoomType()))
            {
                roomPrice = roomPriceChangeEvent.getNewPrice();
            }
        }
        ReservationMadeEvent reservationMadeEvent = new ReservationMadeEvent(UUID.randomUUID(),
                makeNewReservationCommand.getUuid(), makeNewReservationCommand.getTimeStamp(),
                makeNewReservationCommand.getStartDate(), makeNewReservationCommand.getEndDate(),
                makeNewReservationCommand.getNumberOfAdults(), makeNewReservationCommand.getNumberOfChildrenUnder10(), makeNewReservationCommand.getNumberOfChildrenUnder18(), hotelUuid, suitableRoomUuid, roomPrice);
        reservationEventRepository.save(reservationMadeEvent);
        //System.out.println(reservationEventRepository.findAll());
        return Optional.of(reservationMadeEvent);
        //rabbitTemplate.convertAndSend("reservation-made-queue", reservationMadeEvent);
    }
    public ReservationCancelledEvent cancelReservation(CancelReservationCommand cancelReservationCommand) {
        ReservationCancelledEvent reservationCancelledEvent = new ReservationCancelledEvent(UUID.randomUUID(), cancelReservationCommand.getReservationUuid());
        reservationEventRepository.save(reservationCancelledEvent);
        //System.out.println(reservationEventRepository.findAll());
        return reservationCancelledEvent;
        //rabbitTemplate.convertAndSend("reservation-cancelled-queue", reservationCancelledEvent);
    }
}
