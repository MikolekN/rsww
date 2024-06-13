package com.rsww.lydka.TripService.service;

import com.rsww.lydka.TripService.dto.GetFlightsRequest;
import com.rsww.lydka.TripService.dto.GetHotelsRequest;
import com.rsww.lydka.TripService.entity.Hotel;
import com.rsww.lydka.TripService.entity.Reservation;
import com.rsww.lydka.TripService.entity.ReservationInfo;
import com.rsww.lydka.TripService.listener.events.accommodation.response.MakeNewReservationResponse;
import com.rsww.lydka.TripService.listener.events.orders.request.GetAllOrdersRequest;
import com.rsww.lydka.TripService.listener.events.orders.request.OrderInfoRequest;
import com.rsww.lydka.TripService.listener.events.orders.response.GetAllOrdersResponse;
import com.rsww.lydka.TripService.listener.events.orders.response.OrderInfoResponse;
import com.rsww.lydka.TripService.dto.PreferencesRequest;
import com.rsww.lydka.TripService.dto.PreferencesResponse;
import com.rsww.lydka.TripService.listener.events.trip.reservation.PostReservationRequest;
import com.rsww.lydka.TripService.listener.events.trip.reservation.PostReservationResponse;
import com.rsww.lydka.TripService.listener.events.trip.reservation.transport.Flight;
import com.rsww.lydka.TripService.listener.events.trip.reservation.transport.FlightReservation;
import com.rsww.lydka.TripService.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.*;

@Service
public class TripService {
    private final Logger logger = LoggerFactory.getLogger(TripService.class);
    private final DelegatingAccommodationService accommodationService;
    private final TransportService transportService;
    private final ReservationRepository reservationRepository;

    private final CancellingService cancellingService;
    private final DelegatingAccommodationService delegatingAccommodationService;

    @Autowired
    public TripService(final DelegatingAccommodationService accommodationService,
                       final TransportService transportService,
                       final ReservationRepository reservationRepository, CancellingService cancellingService, DelegatingAccommodationService delegatingAccommodationService) {
        this.accommodationService = accommodationService;
        this.transportService = transportService;
        this.reservationRepository = reservationRepository;
        this.cancellingService = cancellingService;
        this.delegatingAccommodationService = delegatingAccommodationService;
    }

    public void confirmReservation(UUID reservationId) {
        List<Reservation> res = reservationRepository.findReservationsByReservationId(reservationId.toString());
        if(res.isEmpty())
            return;
        Reservation reservation = res.get(res.size() - 1);
        if (reservation.getTripId() != null && reservation.getTripId().equals("Cancelled"))
        {
            return;
        }
        reservation.setPayed(true);
        reservationRepository.save(reservation);

    }

    public PostReservationResponse reserveTrip(final PostReservationRequest request, String requestNumber) {
        logger.info("{} Started reservation.", requestNumber);

        String reservationId = UUID.randomUUID().toString();
        LocalDateTime reservationTime = LocalDateTime.now();

        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationId);
        reservation.setUser(request.getUsername());
        reservation.setPayed(false);
        reservation.setReservationTime(reservationTime);

        PostReservationResponse response = new PostReservationResponse(request.getUuid(), false, reservationId);

        MakeNewReservationResponse hotelReservation = accommodationService.reserve(reservationId,
                reservationTime.toString(),
                request.getHotelUuid(),
                request.getRoomType(),
                request.getDateFrom(),
                request.getDateTo(),
                request.getNumberOfAdults(),
                request.getNumberOfChildrenUnder10(),
                request.getNumberOfChildrenUnder18());
        logger.info("{} {} hotel reservation.", requestNumber, hotelReservation.isSuccessful() ? "Successful" : "Unsuccessful");
        if (hotelReservation == null || !hotelReservation.isSuccessful()) {
            response.setReservationId(null);
            return response;
        }
        reservation.setHotelId(hotelReservation.getReservationMadeEvent().getHotel().toString());
        // TODO: reservation.setRoomId


        String people_count = String.valueOf(Integer.parseInt(request.getNumberOfAdults()) + Integer.parseInt(request.getNumberOfChildrenUnder10()) + Integer.parseInt(request.getNumberOfChildrenUnder18()));
        FlightReservation startFlightReservation = transportService.reserve(request.getFlightToUuid(), "0", people_count);
        logger.info("{} {} start flight reservation.", requestNumber, startFlightReservation.isSuccessfullyReserved() ? "Successful" : "Unsuccessful");
        if (!startFlightReservation.isSuccessfullyReserved()) {
            String cancellationTime = LocalDateTime.now().toString();
            accommodationService.cancelReservation(UUID.randomUUID().toString(), cancellationTime, reservationId);
            response.setReservationId(null);
            return response;
        }
        reservation.setStartFlightId(startFlightReservation.getFlight().getId().toString());
        reservation.setStartFlightReservation(startFlightReservation.getId().toString());

        FlightReservation endFlightReservation = transportService.reserve(request.getFlightFromUuid(), "0", people_count);
        logger.info("{} {} flights reservation.", requestNumber, endFlightReservation.isSuccessfullyReserved() ? "Successful" : "Unsuccessful");
        if (!endFlightReservation.isSuccessfullyReserved()) {
            transportService.cancel(startFlightReservation.getId().toString(), startFlightReservation.getPeopleCount());
            String cancellationTime = LocalDateTime.now().toString();
            accommodationService.cancelReservation(UUID.randomUUID().toString(), cancellationTime, reservationId);
            return response;
        }
        reservation.setEndFlightId(endFlightReservation.getFlight().getId().toString());
        reservation.setEndFlightReservation(endFlightReservation.getId().toString());

        int numberOfAdults = Integer.parseInt(request.getNumberOfAdults());
        int numberOfChildrenUnder10 = Integer.parseInt(request.getNumberOfChildrenUnder10());
        int numberOfChildrenUnder18 = Integer.parseInt(request.getNumberOfChildrenUnder18());

        float roomPrice = hotelReservation.getReservationMadeEvent().getRoomPrice();
        float fullHotelPrice = (roomPrice * numberOfAdults) + (roomPrice * numberOfChildrenUnder10 * 0.5f) + (roomPrice * numberOfChildrenUnder18 * 0.7f);
        float flightsPrice = (numberOfAdults + numberOfChildrenUnder18 + numberOfChildrenUnder10) * ((startFlightReservation.getFlight().getPrice()) + (endFlightReservation.getFlight().getPrice()));
        logger.info("{} {} hotel cena, lot cena", fullHotelPrice, flightsPrice);
        float price = fullHotelPrice + flightsPrice;
        reservation.setPrice((int)price);

        reservationRepository.save(reservation);

        response.setResponse(true);

        cancellingService.checkPayment(reservation, numberOfAdults + numberOfChildrenUnder18 + numberOfChildrenUnder10);
        return response;
    }
    @Async
    public void checkPayment(Reservation reservation, int numberOfPeople) {
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        List<Reservation> res = reservationRepository.findReservationsByReservationId(reservation.getReservationId());
        if(res.isEmpty())
            return;
        Reservation reservationToCheck = res.get(0);
        if(reservationToCheck.getPayed())
            return;
        logger.info("Reservation {} was cancelled, because it wasn't paid.", reservation.getReservationId());
        transportService.cancel(reservation.getStartFlightId(), numberOfPeople);
        transportService.cancel(reservation.getEndFlightId(), numberOfPeople);
        String cancellationTime = LocalDateTime.now().toString();
        accommodationService.cancelReservation(UUID.randomUUID().toString(), cancellationTime, reservationToCheck.getReservationId());
        reservation.setTripId("Cancelled");
        reservationRepository.save(reservation);
    }

    public GetAllOrdersResponse getAllOrders(GetAllOrdersRequest request) {
        List<Reservation> reservations = reservationRepository.findAllByUser(request.getUsername());
        return new GetAllOrdersResponse(reservations);
    }

    public PreferencesResponse getPreferences(PreferencesRequest request, String requestNumber) {
        logger.info("{} Started preferences.", requestNumber);
        PreferencesResponse response = new PreferencesResponse(request.getUuid(), false, List.of());

        try {
            List<Reservation> reservations = reservationRepository.findAllByUser(request.getUsername());
            Collections.reverse(reservations);

            GetHotelsRequest getHotelsRequest = new GetHotelsRequest(UUID.randomUUID());
            List<Hotel> hotels = delegatingAccommodationService.getAllHotels(getHotelsRequest).getHotels();

            List<ReservationInfo> reservationInfos = new ArrayList<>();

            for (Reservation reservation : reservations) {

                Flight flight = transportService.getFlight(reservation.getStartFlightReservation());

                Hotel hotel = hotels.stream()
                        .filter(h -> h.getUuid().equals(reservation.getHotelId()))
                        .findFirst()
                        .orElse(null);

                ReservationInfo reservationInfo = new ReservationInfo(reservation.getReservationId(), reservation.getUser(), reservation.getPayed(),
                        reservation.getReservationTime(), reservation.getStartFlightReservation(), reservation.getEndFlightReservation(),
                        reservation.getStartFlightId(), reservation.getEndFlightId(), reservation.getHotelReservation(), reservation.getTripId(),
                        reservation.getHotelId(), reservation.getPrice(), hotel.getName(), hotel.getCountry(), flight.getDepartureAirport(),
                        flight.getDepartureCountry(), flight.getArrivalAirport(), flight.getArrivalCountry());

                reservationInfos.add(reservationInfo);
            }

            response.setPreferences(reservationInfos);
            response.setResponse(true);
            return response;
        } catch (Exception e) {
            return response;
        }
    }

    public OrderInfoResponse reservationInfo(OrderInfoRequest request) {
        List<Reservation> reservations = reservationRepository.findReservationsByReservationId(request.getReservationId());
        if (reservations.isEmpty()) {
            return new OrderInfoResponse(null);
        }
        Reservation reservation = reservations.get(reservations.size() - 1);
        return new OrderInfoResponse(reservation);
    }
}
