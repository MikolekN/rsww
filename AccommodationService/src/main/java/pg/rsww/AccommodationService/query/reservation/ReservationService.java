package pg.rsww.AccommodationService.query.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pg.rsww.AccommodationService.command.entity.ReservationCancelledEvent;
import pg.rsww.AccommodationService.command.entity.ReservationMadeEvent;
import pg.rsww.AccommodationService.command.entity.RoomAddedEvent;
import pg.rsww.AccommodationService.query.entity.Reservation;
import pg.rsww.AccommodationService.query.entity.Room;
import pg.rsww.AccommodationService.query.room.RoomRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
    public void makeNewReservation(ReservationMadeEvent reservationMadeEvent) {
        reservationRepository.save(Reservation.builder()
                        .uuid(reservationMadeEvent.getReservationUuid().toString())
                        .timeStamp(reservationMadeEvent.getTimeStamp())
                        .startDate(reservationMadeEvent.getStartDate())
                        .endDate(reservationMadeEvent.getEndDate())
                        .hotelUuid(reservationMadeEvent.getHotel().toString())
                        .roomUuid(reservationMadeEvent.getRoom().toString())
                .build());
    }
    public void cancelReservation(ReservationCancelledEvent reservationCancelledEvent) {
        Reservation reservationToRemove = reservationRepository.findByUuid(
                reservationCancelledEvent.getReservationUuid().toString());
        if (reservationToRemove != null) {
            reservationRepository.delete(reservationToRemove);
        }
    }
}
