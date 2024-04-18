package com.example.transportation;

import com.example.transportation.DTO.BookFlightRequest;
import com.example.transportation.Entity.Flight;
import com.example.transportation.Repository.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransportListener {
    private final static Logger logger = LoggerFactory.getLogger(TransportListener.class);
    private final RabbitTemplate rabbitTemplate;
    private final Queue transportInputQueue;
    private FlightRepository repository;

    @Autowired
    public TransportListener(RabbitTemplate rabbitTemplate, Queue transportInputQueue, FlightRepository repository) {
        this.rabbitTemplate = rabbitTemplate;
        this.transportInputQueue = transportInputQueue;
        this.repository = repository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.transport.input}")
    public void receiveMessage(Message<BookFlightRequest> message) {
        BookFlightRequest request = message.getPayload();
        System.out.println(request);
        Flight flight = new Flight(UUID.randomUUID(), "departure", "arrival", "1h",
                50, 0, 80);
    }
}
