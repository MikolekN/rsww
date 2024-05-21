package com.rsww.lydka.TripService.service;

import com.rsww.lydka.TripService.listener.events.accommodation.MakeNewReservationResponse;
import com.rsww.lydka.TripService.listener.events.orders.*;
import com.rsww.lydka.TripService.listener.events.trip.reservation.PostReservationRequest;
import com.rsww.lydka.TripService.listener.events.trip.reservation.PostReservationResponse;
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

    @Autowired
    public TripService(final DelegatingAccommodationService accommodationService,
                       final TransportService transportService,
                       final ReservationRepository reservationRepository, CancellingService cancellingService) {
        this.accommodationService = accommodationService;
        this.transportService = transportService;
        this.reservationRepository = reservationRepository;
        this.cancellingService = cancellingService;
    }

    public void confirmReservation(UUID reservationId) {
        Optional<ReservationRepository.Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent()) {
            if (reservation.get().getTripId() != null && reservation.get().getTripId().equals("Cancelled"))
            {
                return;
            }
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
        FlightReservation startFlightReservation = transportService.reserve(request.getFlightToUuid(), "0", people_count);
        logger.info("{} {} start flight reservation.", requestNumber, startFlightReservation.isSuccessfullyReserved() ? "Successful" : "Unsuccessful");
        if (!startFlightReservation.isSuccessfullyReserved()) {
            String cancellationTime = LocalDateTime.now().toString();
            accommodationService.cancelReservation(UUID.randomUUID().toString(), cancellationTime, reservationId);
            response.setReservationId(null);
            return response;
        }
        reservation.setStartFlightId(startFlightReservation.getId().toString());

        FlightReservation endFlightReservation = transportService.reserve(request.getFlightFromUuid(), "0", people_count);
        logger.info("{} {} flights reservation.", requestNumber, endFlightReservation.isSuccessfullyReserved() ? "Successful" : "Unsuccessful");
        if (!endFlightReservation.isSuccessfullyReserved()) {
            transportService.cancel(startFlightReservation.getId().toString(), startFlightReservation.getPeopleCount());
            String cancellationTime = LocalDateTime.now().toString();
            accommodationService.cancelReservation(UUID.randomUUID().toString(), cancellationTime, reservationId);
            return response;
        }
        reservation.setEndFlightId(endFlightReservation.getId().toString());

        int numberOfAdults = Integer.parseInt(request.getNumberOfAdults());
        int numberOfChildrenUnder10 = Integer.parseInt(request.getNumberOfChildrenUnder10());
        int numberOfChildrenUnder18 = Integer.parseInt(request.getNumberOfChildrenUnder18());

        float roomPrice = hotelReservation.getReservationMadeEvent().getRoomPrice();
        float fullHotelPrice = (roomPrice * numberOfAdults) + (roomPrice * numberOfChildrenUnder10 * 0.5f) + (roomPrice * numberOfChildrenUnder18 * 0.7f);
        float flightsPrice = (numberOfAdults + numberOfChildrenUnder18 + numberOfChildrenUnder10) * ((startFlightReservation.getFlight().getPrice()) + (endFlightReservation.getFlight().getPrice()));
        float price = fullHotelPrice + flightsPrice;
        reservation.setPrice((double) price);

        reservationRepository.save(reservation);

        response.setResponse(true);

        cancellingService.checkPayment(reservation, numberOfAdults + numberOfChildrenUnder18 + numberOfChildrenUnder10);
        return response;
    }
    @Async
    public void checkPayment(ReservationRepository.Reservation reservation, int numberOfPeople) {
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        List<ReservationRepository.Reservation> res = reservationRepository.findReservationsByReservationId(reservation.getReservationId());
        if(res.isEmpty())
            return;
        ReservationRepository.Reservation reservationToCheck = res.get(0);
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
        List<ReservationRepository.Reservation> reservations = reservationRepository.findAllByUser(request.getUsername());
        return new GetAllOrdersResponse(reservations);
    }

    public OrderInfoResponse reservationInfo(OrderInfoRequest request) {
        Optional<ReservationRepository.Reservation> reservation = reservationRepository.findById(UUID.fromString(request.getReservationId()));
        return reservation.map(OrderInfoResponse::new).orElseGet(() -> new OrderInfoResponse(null));
    }
}
