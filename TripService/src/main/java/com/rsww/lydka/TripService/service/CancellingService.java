package com.rsww.lydka.TripService.service;

import com.rsww.lydka.TripService.entity.Reservation;
import com.rsww.lydka.TripService.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CancellingService {
    private final Logger logger = LoggerFactory.getLogger(CancellingService.class);
    private final DelegatingAccommodationService accommodationService;
    private final TransportService transportService;
    private final ReservationRepository reservationRepository;

    @Autowired
    public CancellingService(DelegatingAccommodationService accommodationService, TransportService transportService, ReservationRepository reservationRepository) {
        this.accommodationService = accommodationService;
        this.transportService = transportService;
        this.reservationRepository = reservationRepository;
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
        Reservation reservationToCheck = res.get(res.size() - 1);

        if(reservationToCheck.getPayed())
            return;

        logger.info("Reservation {} was cancelled, because it wasn't paid.", reservation.getReservationId());
        transportService.cancel(reservationToCheck.getStartFlightReservation(), numberOfPeople);
        transportService.cancel(reservationToCheck.getEndFlightReservation(), numberOfPeople);
        String cancellationTime = LocalDateTime.now().toString();

        accommodationService.cancelReservation(UUID.randomUUID().toString(), cancellationTime, reservationToCheck.getReservationId());

        reservationToCheck.setTripId("Cancelled");
        reservationRepository.save(reservationToCheck);
    }
}
