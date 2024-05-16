package com.rsww.lydka.TripService.service;

import com.rsww.lydka.TripService.listener.events.accommodation.MakeNewReservationResponse;
import com.rsww.lydka.TripService.listener.events.orders.GetAllOrdersRequest;
import com.rsww.lydka.TripService.listener.events.orders.GetAllOrdersResponse;
import com.rsww.lydka.TripService.listener.events.trip.reservation.PostReservationRequest;
import com.rsww.lydka.TripService.listener.events.trip.reservation.PostReservationResponse;
import com.rsww.lydka.TripService.listener.events.trip.reservation.transport.FlightReservation;
import com.rsww.lydka.TripService.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.*;

@Service
public class TripService {
    private final Logger logger = LoggerFactory.getLogger(TripService.class);
    private final DelegatingAccommodationService accommodationService;
    private final TransportService transportService;
    private final ReservationRepository reservationRepository;

    @Autowired
    public TripService(final DelegatingAccommodationService accommodationService,
                       final TransportService transportService,
                       final ReservationRepository reservationRepository) {
        this.accommodationService = accommodationService;
        this.transportService = transportService;
        this.reservationRepository = reservationRepository;
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
        FlightReservation startFlightReservation = transportService.reserve(request.getFlightToUuid(), request.getUsername(), people_count);
        logger.info("{} {} start flight reservation.", requestNumber, startFlightReservation.isSuccessfullyReserved() ? "Successful" : "Unsuccessful");
        if (!startFlightReservation.isSuccessfullyReserved()) {
            String cancellationTime = LocalDateTime.now().toString();
            accommodationService.cancelReservation(UUID.randomUUID().toString(), cancellationTime, reservationId);
            response.setReservationId(null);
            return response;
        }
        reservation.setStartFlightId(startFlightReservation.getId());

        FlightReservation endFlightReservation = transportService.reserve(request.getFlightFromUuid(), request.getUsername(), people_count);
        logger.info("{} {} flights reservation.", requestNumber, endFlightReservation.isSuccessfullyReserved() ? "Successful" : "Unsuccessful");
        if (!endFlightReservation.isSuccessfullyReserved()) {
            transportService.cancel(startFlightReservation.getId());
            String cancellationTime = LocalDateTime.now().toString();
            accommodationService.cancelReservation(UUID.randomUUID().toString(), cancellationTime, reservationId);
            return response;
        }
        reservation.setEndFlightId(endFlightReservation.getId());

        int numberOfAdults = Integer.parseInt(request.getNumberOfAdults());
        int numberOfChildrenUnder10 = Integer.parseInt(request.getNumberOfChildrenUnder10());
        int numberOfChildrenUnder18 = Integer.parseInt(request.getNumberOfChildrenUnder18());

        float roomPrice = hotelReservation.getReservationMadeEvent().getRoomPrice();
        float fullHotelPrice = (roomPrice * numberOfAdults) + (roomPrice * numberOfChildrenUnder10 * 0.5f) + (roomPrice * numberOfChildrenUnder18 * 0.7f);
        float flightsPrice = (numberOfAdults + numberOfChildrenUnder18 + numberOfChildrenUnder10) * (Float.parseFloat(startFlightReservation.getFlight().getPrice()) + Float.parseFloat(endFlightReservation.getFlight().getPrice()));
        float price = fullHotelPrice + flightsPrice;
        reservation.setPrice((double) price);

        reservationRepository.save(reservation);

        response.setResponse(true);
        return response;
    }

    public GetAllOrdersResponse getAllOrders(GetAllOrdersRequest request) {
        List<ReservationRepository.Reservation> reservations = reservationRepository.findAllByUser(request.getUsername());
        return new GetAllOrdersResponse(reservations);
    }
}
