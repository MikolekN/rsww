package com.example.transportation;

import com.example.transportation.DTO.GetFlightInfoRequest;
import com.example.transportation.DTO.GetFlightsInfoResponse;
import com.example.transportation.flight.domain.Flight;
import com.example.transportation.flight.repo.FlightRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Component
public class FindFlightsListener {
    private final FlightRepository repository;

    @Autowired
    public FindFlightsListener(FlightRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.findFlightQueue}")
    public GetFlightsInfoResponse getFlights(GetFlightInfoRequest flightRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(flightRequest.getFlightDate(), formatter);

        List<Flight> flights = repository.findAllByDepartureDate(date);
        return new GetFlightsInfoResponse(flights);
    }
}
