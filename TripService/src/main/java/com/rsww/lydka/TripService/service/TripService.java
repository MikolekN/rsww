package com.rsww.lydka.TripService.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rsww.lydka.TripService.entity.Trip;
import com.rsww.lydka.TripService.listener.events.orders.GetAllOrdersRequest;
import com.rsww.lydka.TripService.listener.events.orders.GetAllOrdersResponse;
import com.rsww.lydka.TripService.listener.events.trip.TripsRequest;
import com.rsww.lydka.TripService.listener.events.trip.TripsResponse;
import com.rsww.lydka.TripService.listener.events.trip.reservation.PostReservationRequest;
import com.rsww.lydka.TripService.listener.events.trip.reservation.PostReservationResponse;
import com.rsww.lydka.TripService.repository.ReservationRepository;
import com.rsww.lydka.TripService.repository.TripRepository;
import com.rsww.lydka.TripService.entity.Flight;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TripService {
    private final Logger logger = LoggerFactory.getLogger(TripService.class);
    private final TripRepository tripRepository;
    private final DelegatingAccommodationService accommodationService;
    private final DelegatingTransportService transportService;
    private final PaymentService paymentService;
    private final ReservationRepository reservationRepository;

    @Autowired
    public TripService(final TripRepository tripRepository,
                       final DelegatingAccommodationService accommodationService,
                       final DelegatingTransportService transportService,
                       final PaymentService paymentService,
                       final ReservationRepository reservationRepository) {
        this.tripRepository = tripRepository;
        this.accommodationService = accommodationService;
        this.transportService = transportService;
        this.paymentService = paymentService;
        this.reservationRepository = reservationRepository;
    }

    public List<Trip> getTrips(TripsRequest request) {
        int people3To9 = request.getPeople3To9() == null ? 0 : request.getPeople3To9();
        int people10To17 = request.getPeople10To17() == null ? 0 : request.getPeople10To17();
        int adults = request.getAdults() == null ? 0 : request.getAdults();

        int requiredSits = people3To9 + people10To17 + adults;
        final var trips = tripRepository.findAll();
        final var hotels = accommodationService.getHotels(DelegatingAccommodationService.SearchParams.builder()
                .adults(request.getAdults())
                .destination(request.getDestination())
                .people3To9(request.getPeople3To9())
                .people10To17(request.getPeople10To17())
                .build())
                .stream()
                .map(hotel -> hotel.getHotelId())
                .collect(Collectors.toSet());

        final var transports = transportService.getTransports(request.getDeparture(),
                        "",
                        request.getStartDate(),
                        "")
                .stream()
                .filter(transport -> transport.getSitsCount() - transport.getSitsOccupied() >= requiredSits)
                .map(Flight::getFlightId)
                .collect(Collectors.toSet());

        return trips.stream()
                .filter(trip -> hotels.contains(trip.getHotelId()))
                .filter(trip -> transports.contains(trip.getFromFlightId()))
                .collect(Collectors.toList());

    }

    public void confirmReservation(UUID reservationId) {
        Optional<ReservationRepository.Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent()) {
            reservation.get().setPayed(true);
            reservationRepository.save(reservation.get());
        }
    }

    public PostReservationResponse reserveTrip(final PostReservationRequest request, String requestNumber) {
        logger.info("{} Started reservation.", requestNumber);

        String reservationId = UUID.randomUUID().toString();
        LocalDateTime reservationTime = LocalDateTime.now();

        ReservationRepository.Reservation reservation = new ReservationRepository.Reservation();
        reservation.setReservationId(reservationId);
        reservation.setUser(request.getUsername());
        reservation.setPayed(false);
        reservation.setReservationTime(reservationTime);

        PostReservationResponse response = new PostReservationResponse(request.getUuid(), false, reservationId);

        // TODO: reserve accommodation
        var hotelReservation = accommodationService.reserve(reservationId,
                reservationTime.toString(),
                request.getHotelUuid(),
                request.getRoomType(),
                request.getDateFrom(),
                request.getDateTo(),
                request.getNumberOfAdults(),
                request.getNumberOfChildrenUnder10(),
                request.getNumberOfChildrenUnder18());
        logger.info("{} {} hotel reservation.", requestNumber, true ? "Successful" : "Unsuccessful");
        // TODO: if unsuccessful cancel reservation
        reservation.setHotelId(hotelReservation.getHotelId());

        // TODO: reserve flights
        logger.info("{} {} flights reservation.", requestNumber, true ? "Successful" : "Unsuccessful");
        // TODO: if unsuccessful cancel accommodation and reservation
        reservation.setStartFlightId(startFlightReservation.getStartFlightId());
        reservation.setEndFlightId(endFlightReservation.getEndFlightId());

        // TODO: calculate price?
        reservation.setPrice(price);

        reservationRepository.save(reservation);

        response.setResponse(true);
        return response;
    }

    public List<TripsResponse.Trip> getReservations(Long userId) {
        final var reservations = reservationRepository.findAllByUserId(userId);
        final var trips = tripRepository.findAllByUuidIn(reservations.stream().map(ReservationRepository.Reservation::getTripId).collect(Collectors.toSet()));
        return reservations.stream().map(reservation -> {
            final var maybeTrip = trips.parallelStream().filter(t -> t.getTripId().equals(reservation.getTripId())).findFirst();
            if (maybeTrip.isEmpty()) {
                return null;
            }
            final var trip = maybeTrip.get();
            final var hotel = accommodationService.getHotel(trip.getHotelId()).get();
            final var startFlight = transportService.getTransport(trip.getFromFlightId()).get();
            final var endFlight = transportService.getTransport(trip.getToFlightId()).get();
            return TripsResponse.Trip.builder()
                    .tripId(reservation.getReservationId())
                    .tripPrice(reservation.getPrice())
                    .dateStart(startFlight.getDepartureDate())
                    .dateEnd(endFlight.getDepartureDate())
                    .hotel(TripsResponse.Hotel.builder()
                            .hotelId(hotel.getHotelId())
                            .name(hotel.getName())
                            .stars(hotel.getStars())
                            .country(hotel.getCountry())
                            .build())
                    .build();
        }).collect(Collectors.toList());
    }
    public GetAllOrdersResponse getAllOrders(GetAllOrdersRequest request) {
        //List<ReservationRepository.Reservation> reservations = reservationRepository.findAllByUserId(request.getUsername());
        return new GetAllOrdersResponse(new ArrayList<>());
    }
}
