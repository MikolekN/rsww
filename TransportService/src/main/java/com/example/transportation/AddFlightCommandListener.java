package com.example.transportation;

import com.example.transportation.command.AddFlightCommand;
import com.example.transportation.Entity.Flight;
import com.example.transportation.Repository.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class AddFlightCommandListener {
    private final FlightRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final Queue addFlightDataStore;

    @Autowired
    public AddFlightCommandListener(FlightRepository repository, RabbitTemplate rabbitTemplate, Queue addFlightDataStore) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
        this.addFlightDataStore = addFlightDataStore;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.addFlightQueue}")
    public void receiveMessage(Message<AddFlightCommand> message) {
        AddFlightCommand command = message.getPayload();
        Flight flight = AddFlightCommand.commandToEntityMapper(command);
        Flight savedFlight = repository.save(flight);
        rabbitTemplate.convertAndSend(addFlightDataStore.getName(), savedFlight);
    }
}
