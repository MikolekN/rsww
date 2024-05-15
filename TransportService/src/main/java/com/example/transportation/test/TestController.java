package com.example.transportation.test;

import com.example.transportation.DTO.GetFlightInfoRequest;
import com.example.transportation.event.domain.EventTypeFlight;
import com.example.transportation.event.domain.FlightAddedEvent;
import com.example.transportation.event.repo.EventTypeRepository;
import com.example.transportation.event.repo.FlightAddedEventRepository;
import com.example.transportation.flight.domain.Flight;
import com.example.transportation.flight.repo.FlightRepository;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
public class TestController {
    private final FlightRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final Queue addFlightQueue;
    private final Queue findFlightQueue;

    private final EventTypeRepository eventTypeRepository;
    private final FlightAddedEventRepository far;
    private final FlightRepository flightRepo;

    @Autowired
    public TestController(FlightRepository repository, RabbitTemplate rabbitTemplate, Queue addFlightQueue,
                          Queue findFlightQueue, EventTypeRepository eventTypeRepository, FlightAddedEventRepository far,
                          FlightRepository flightRepo) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
        this.addFlightQueue = addFlightQueue;
        this.findFlightQueue = findFlightQueue;
        this.eventTypeRepository = eventTypeRepository;
        this.far = far;
        this.flightRepo = flightRepo;
    }

    @PostMapping("/getFlightByDate")
    String getFlight(@RequestBody GetFlightInfoRequest request) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(request.getFlightDate());
            List<Flight> flights = flightRepo.findAllByDepartureDateIgnoringTime(date);
            return flights.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    @PostMapping("/createEventType")
    String createEventType() {
        eventTypeRepository.save(new EventTypeFlight(1, "test"));
        return "event added";
    }

}
