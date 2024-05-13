package com.example.transportation;

import com.example.transportation.command.AddFlightCommand;
import com.example.transportation.event.domain.FlightAddedEvent;
import com.example.transportation.event.repo.FlightAddedEventRepository;
import com.example.transportation.flight.domain.Flight;
import com.example.transportation.flight.repo.FlightRepository;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddFlightCommandListener {
    private final FlightRepository repository;
    private final FlightAddedEventRepository eventRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Queue addFlightDataStore;

    @Autowired
    public AddFlightCommandListener(FlightRepository repository, RabbitTemplate rabbitTemplate, Queue addFlightDataStore,
                                    FlightAddedEventRepository eventRepository) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
        this.addFlightDataStore = addFlightDataStore;
        this.eventRepository = eventRepository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.addFlightQueue}")
    public void receiveMessage(Message<AddFlightCommand> message) {
        AddFlightCommand command = message.getPayload();
        Flight flight = AddFlightCommand.commandToEntityMapper(command);
        Flight savedFlight = repository.save(flight);

        eventRepository.save(new FlightAddedEvent(
                UUID.randomUUID(),
                savedFlight.getId(),
                savedFlight.getDepartureAirport(),
                savedFlight.getDepartureCountry(),
                savedFlight.getArrivalAirport(),
                savedFlight.getArrivalCountry(),
                savedFlight.getDepartureDate(),
                savedFlight.getArrivalDate(),
                savedFlight.getTravelTime(),
                savedFlight.getSitsCount(),
                savedFlight.getSitsOccupied(),
                savedFlight.getPrice()
        ));

        rabbitTemplate.convertAndSend(addFlightDataStore.getName(), savedFlight);
    }
}
