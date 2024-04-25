package pg.rsww.AccommodationService.query.hotel;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pg.rsww.AccommodationService.query.entity.Hotel;
import pg.rsww.AccommodationService.command.entity.HotelAddedEvent;
import pg.rsww.AccommodationService.query.entity.Reservation;
import pg.rsww.AccommodationService.query.entity.Room;
import pg.rsww.AccommodationService.query.event.GetAllHotelsRequest;
import pg.rsww.AccommodationService.query.event.GetAllHotelsResponse;
import pg.rsww.AccommodationService.query.event.GetHotelInfoRequest;
import pg.rsww.AccommodationService.query.event.GetHotelInfoResponse;
import pg.rsww.AccommodationService.query.reservation.ReservationRepository;
import pg.rsww.AccommodationService.query.room.RoomRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository, RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    public void addNewHotel(HotelAddedEvent hotelAddedEvent) {
        hotelRepository.save(Hotel.builder()
                        .uuid(hotelAddedEvent.getHotelUuid().toString())
                        .name(hotelAddedEvent.getName())
                        .country(hotelAddedEvent.getCountry())
                .build());
    }

    public GetAllHotelsResponse getAllHotels(GetAllHotelsRequest getAllHotelsRequest) {
        List<Hotel> hotels;
        if (!getAllHotelsRequest.getCountry().isEmpty()) {
            hotels = hotelRepository.findAllByCountry(getAllHotelsRequest.getCountry());
        }
        else {
            hotels = hotelRepository.findAll();
        }
        /*
        use - getAllHotelsRequest.getNumberOfAdults(), getAllHotelsRequest.getNumberOfChildren()
        to get Hotels that have rooms with this exact capacity
        use - getAllHotelsRequest.getStartDate(), getAllHotelsRequest.getEndDate()
             if they are not empty, to filter if any of these rooms are free during this period
        * */

        // Removing unavailable hotels
        Iterator<Hotel> it = hotels.iterator();
        while (it.hasNext()) {
            Hotel hotel = it.next();
            List<Room> rooms = roomRepository.findAllByHotelUuidAndNumberOfAdultsAndNumberOfChildren(
                    hotel.getUuid(),
                    getAllHotelsRequest.getNumberOfAdults(),
                    getAllHotelsRequest.getNumberOfChildren());
            if(rooms.isEmpty())
                it.remove();
            else {
                Optional<Room> availableRoom = rooms.stream().filter(room -> {
                    List<Reservation> reservations = reservationRepository.findAllByHotelUuidAndRoomUuid(
                            hotel.getUuid(), room.getUuid());
                    if (reservations.isEmpty())
                        return true;
                    else {
                        for (Reservation reservation: reservations) {
                            // TODO Check if it works
                            if (!(getAllHotelsRequest.getStartDate().isBefore(reservation.getStartDate()) || getAllHotelsRequest.getStartDate().isAfter(reservation.getEndDate()))) {
                                return false;
                            }
                            else if (!(getAllHotelsRequest.getEndDate().isBefore(reservation.getStartDate()) || getAllHotelsRequest.getEndDate().isAfter(reservation.getEndDate()))) {
                                return false;
                            }
                        }
                        return true;
                    }
                }).findFirst();
                if(availableRoom.isEmpty()) {
                    it.remove();
                }
            }
        }

        GetAllHotelsResponse response = GetAllHotelsResponse.builder()
                .hotelsList(hotels.stream().map(hotel -> GetAllHotelsResponse.HotelShortModel.builder()
                        .uuid(UUID.fromString(hotel.getUuid()))
                        .name(hotel.getName())
                        .country(hotel.getCountry())
                        .build()).toList())
                .requestUuid(getAllHotelsRequest.getRequestUuid())
                .build();
        //rabbitTemplate.convertAndSend("hotel-all-response-queue", response);
        return response;
        //System.out.println("RESPONSE = " + response);
    }
    public GetHotelInfoResponse getHotelInfo(GetHotelInfoRequest getHotelInfoRequest) {
        Hotel hotel = hotelRepository.findHotelByUuid(getHotelInfoRequest.getHotelUuid().toString());
        if (hotel == null) {
            //rabbitTemplate.convertAndSend("hotel-info-response-queue", response);
            return GetHotelInfoResponse.builder()
                    .resultFound(false)
                    .requestUuid(getHotelInfoRequest.getRequestUuid())
                    .build();
        }
        List<Room> rooms = roomRepository.findAllByHotelUuidAndNumberOfAdultsAndNumberOfChildren(
                getHotelInfoRequest.getHotelUuid().toString(),
                getHotelInfoRequest.getNumberOfAdults(),
                getHotelInfoRequest.getNumberOfChildren());
        // Filtering unavailable rooms in the period from the request
        rooms = rooms.stream().filter(room -> {
            List<Reservation> reservations = reservationRepository.findAllByHotelUuidAndRoomUuid(
                    hotel.getUuid(), room.getUuid());
            if (reservations.isEmpty())
                return true;
            else {
                for (Reservation reservation: reservations) {
                    // TODO Check if it works
                    if (!(getHotelInfoRequest.getStartDate().isBefore(reservation.getStartDate()) || getHotelInfoRequest.getStartDate().isAfter(reservation.getEndDate()))) {
                        return false;
                    }
                    else if (!(getHotelInfoRequest.getEndDate().isBefore(reservation.getStartDate()) || getHotelInfoRequest.getEndDate().isAfter(reservation.getEndDate()))) {
                        return false;
                    }
                }
                return true;
            }
        }).toList();

        Set<String> roomTypesAvailable = rooms.stream().map(Room::getType).collect(Collectors.toCollection(TreeSet::new));
        if (roomTypesAvailable.isEmpty()) {
            //rabbitTemplate.convertAndSend("hotel-info-response-queue", response);
            return GetHotelInfoResponse.builder()
                    .resultFound(false)
                    .requestUuid(getHotelInfoRequest.getRequestUuid())
                    .build();
        }

        //rabbitTemplate.convertAndSend("hotel-info-response-queue", response);
        //System.out.println("RESPONSE = " + response);
        return GetHotelInfoResponse.builder()
                .resultFound(true)
                .hotelUuid(UUID.fromString(hotel.getUuid()))
                .name(hotel.getName())
                .country(hotel.getCountry())
                .roomTypes(new ArrayList<>(roomTypesAvailable))
                .requestUuid(getHotelInfoRequest.getRequestUuid())
                .build();
    }
}
