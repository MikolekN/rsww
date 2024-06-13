package com.example.transportation;

import com.example.transportation.DTO.GetFlightInfoRequest;
import com.example.transportation.DTO.GetFlightsInfoResponse;
import com.example.transportation.flight.domain.Flight;
import com.example.transportation.flight.domain.FlightReservation;
import com.example.transportation.flight.repo.FlightRepository;
import com.example.transportation.flight.repo.FlightReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class FindFlightsListener {
    private final Logger logger = LoggerFactory.getLogger(FindFlightsListener.class);
    private final FlightRepository repository;
    private final FlightReservationRepository reservationRepository;

    @Autowired
    public FindFlightsListener(FlightRepository repository, FlightReservationRepository reservationRepository) {
        this.repository = repository;
        this.reservationRepository = reservationRepository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.findFlightQueue}")
    public GetFlightsInfoResponse getFlights(GetFlightInfoRequest flightRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(flightRequest.getFlightDate(), formatter);

        List<Flight> flights = repository.findAllByDepartureDate(date);
        return new GetFlightsInfoResponse(flights);
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.GetFlightQueue}")
    public Flight getFlight(String flightId) {
        Optional<FlightReservation> optionalFlightReservation = reservationRepository.findById(UUID.fromString(flightId));
        if (optionalFlightReservation.isEmpty()) {
           return null;
        }
        FlightReservation reservation = optionalFlightReservation.get();

        Flight flight = reservation.getFlight();
        logger.info("Flight: " + flight);
        return flight;
    }
}
