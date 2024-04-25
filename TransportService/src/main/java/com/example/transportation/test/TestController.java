package com.example.transportation.test;

import com.example.transportation.Repository.FlightRepository;
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

    @Autowired
    public TestController(FlightRepository repository, RabbitTemplate rabbitTemplate, Queue addFlightQueue) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
        this.addFlightQueue = addFlightQueue;
    }
    @PostMapping("/create-flight")
    String newEmployee(@RequestBody AddFlightCommand newFlight) {
        rabbitTemplate.convertAndSend(addFlightQueue.getName(), newFlight);
        return "wysłano";
    }
}
