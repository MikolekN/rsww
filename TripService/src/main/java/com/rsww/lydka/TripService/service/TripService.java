package com.rsww.lydka.TripService.service;

import com.rsww.lydka.TripService.entity.Trip;
import com.rsww.lydka.TripService.listener.events.trip.TripsRequest;
import com.rsww.lydka.TripService.listener.events.trip.TripsResponse;
import com.rsww.lydka.TripService.listener.events.trip.reservation.PostReservationRequest;
import com.rsww.lydka.TripService.repository.ReservationRepository;
import com.rsww.lydka.TripService.repository.TripRepository;
import com.rsww.lydka.TripService.entity.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TripService {
    private final Logger log = LoggerFactory.getLogger(TripService.class);
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

    public Optional<Trip> getTripById(UUID tripId) {
        return tripRepository.findById(tripId.toString());
    }

    public Trip addTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public void removeTrip(UUID tripId) {
        Optional<Trip> trip = tripRepository.findById(tripId.toString());
        trip.ifPresent(tripRepository::delete);
    }

    public void confirmReservation(UUID reservationId) {
        Optional<ReservationRepository.Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent()) {
            reservation.get().setPayed(true);
            reservationRepository.save(reservation.get());
        }
    }

    public boolean reserveTrip(final UUID tripId, final PostReservationRequest.Room room,
                               final String user) {
        final var trip = getTripById(tripId);
        final var hotel = accommodationService.getHotel(trip.get().getHotelId()).get();
        final var startFlight = transportService.getTransport(trip.get().getFromFlightId()).get();
        final var endFlight = transportService.getTransport(trip.get().getToFlightId()).get();

        final var hotelReserved = accommodationService.reserve(hotel, room, startFlight.getDepartureDate(),
                endFlight.getDepartureDate(), user);
        if (!hotelReserved.getSuccess()) {
            return false;
        }

        final Optional<Long> startFlightReserved = transportService.reserve(startFlight.getFlightId(), user, 1);
        if (startFlightReserved.isEmpty()) {
            accommodationService.cancelReservation(hotelReserved.getReservationId());
            return false;
        }

        final Optional<Long> endFlightReserved = transportService.reserve(endFlight.getFlightId(), user, 1);
        if (endFlightReserved.isEmpty()) {
            accommodationService.cancelReservation(hotelReserved.getReservationId());
            transportService.cancelReservation(startFlightReserved.get());
            return false;
        }

        final var reservation = ReservationRepository.Reservation.builder()
                .startFlightReservation(String.valueOf(startFlightReserved.get()))
                .endFlightReservation(String.valueOf(endFlightReserved.get()))
                .userId(Long.parseLong(user))
                .hotelId(hotel.getHotelId())
                .hotelReservation(hotelReserved.getReservationId())
                .reserved(LocalDateTime.now().plusMinutes(1))
                .startFlightId(startFlight.getFlightId())
                .endFlightId(endFlight.getFlightId())
                .payed(false)
                .tripId(String.valueOf(tripId))
                .price(hotelReserved.getPrice() + startFlight.getPrice() + endFlight.getPrice())
                .build();
        reservationRepository.save(reservation);
        return true;
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
}
