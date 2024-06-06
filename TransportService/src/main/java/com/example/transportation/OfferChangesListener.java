package com.example.transportation;

import com.example.transportation.DTO.GetFlightInfoRequest;
import com.example.transportation.DTO.GetFlightsInfoResponse;
import com.example.transportation.flight.domain.Flight;
import com.example.transportation.flight.repo.FlightRepository;
import com.example.transportation.query.GetFlightsRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class OfferChangesListener {
    private final FlightRepository flightRepository;

    public OfferChangesListener(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.GetAllFlightsQueue}")
    public GetFlightsInfoResponse getAllFlights(GetFlightsRequest request) {
        List<Flight> flights = flightRepository.findAll();
        return new GetFlightsInfoResponse(flights);
    }}
