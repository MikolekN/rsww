package com.example.transportation.test;

import com.example.transportation.DTO.GetFlightInfoRequest;
import com.example.transportation.event.domain.EventTypeFlight;
import com.example.transportation.event.repo.EventTypeRepository;
import com.example.transportation.flight.repo.FlightRepository;
import com.example.transportation.command.AddFlightCommand;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final FlightRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final Queue addFlightQueue;
    private final Queue findFlightQueue;

    private final EventTypeRepository eventTypeRepository;

    @Autowired
    public TestController(FlightRepository repository, RabbitTemplate rabbitTemplate, Queue addFlightQueue,
                          Queue findFlightQueue, EventTypeRepository eventTypeRepository) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
        this.addFlightQueue = addFlightQueue;
        this.findFlightQueue = findFlightQueue;
        this.eventTypeRepository = eventTypeRepository;
    }
    @PostMapping("/create-flight")
    String newEmployee(@RequestBody AddFlightCommand newFlight) {
        rabbitTemplate.convertAndSend(addFlightQueue.getName(), newFlight);
        return "wysłano";
    }

    @PostMapping("/getFlightByDate")
    String getFlight(@RequestBody GetFlightInfoRequest request) {
        rabbitTemplate.convertAndSend(findFlightQueue.getName(), request);
        return "Przyjęto " + request.getFlightDate();
    }

    @PostMapping("/createEventType")
    String createEventType() {
        eventTypeRepository.save(new EventTypeFlight(1, "test"));
        return "event added";
    }

}
