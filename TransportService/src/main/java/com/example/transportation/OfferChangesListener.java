package com.example.transportation;

import com.example.transportation.DTO.GetFlightInfoRequest;
import com.example.transportation.DTO.GetFlightsInfoResponse;
import com.example.transportation.command.ChangeFlightPriceCommand;
import com.example.transportation.command.RemoveFlightCommand;
import com.example.transportation.event.domain.FlightChangedEvent;
import com.example.transportation.event.domain.FlightPriceChangedEvent;
import com.example.transportation.event.domain.FlightRemovedEvent;
import com.example.transportation.event.repo.FlightChangedEventRepository;
import com.example.transportation.flight.domain.Flight;
import com.example.transportation.flight.repo.FlightRepository;
import com.example.transportation.query.GetFlightsRequest;
import com.example.transportation.query.GetLastFlightChangesRequest;
import com.example.transportation.query.GetLastFlightChangesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OfferChangesListener {
    private final FlightRepository flightRepository;
    private final FlightChangedEventRepository flightChangedEventRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Queue flightRemovedEventQueue;
    private final Queue flightPriceChangedEventQueue;
    private final static Logger log = LoggerFactory.getLogger(OfferChangesListener.class);

    public OfferChangesListener(FlightRepository flightRepository, FlightChangedEventRepository flightChangedEventRepository, RabbitTemplate rabbitTemplate, Queue flightRemovedEventQueue, Queue flightPriceChangedEventQueue) {
        this.flightRepository = flightRepository;
        this.flightChangedEventRepository = flightChangedEventRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.flightRemovedEventQueue = flightRemovedEventQueue;
        this.flightPriceChangedEventQueue = flightPriceChangedEventQueue;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.GetAllFlightsQueue}")
    public GetFlightsInfoResponse getAllFlights(GetFlightsRequest request) {
        List<Flight> flights = flightRepository.findAll();
        return new GetFlightsInfoResponse(flights);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.RemoveFlightQueue}")
    public void removeFlight(RemoveFlightCommand command) {
        Optional<Flight> optionalFlight = flightRepository.findById(command.getUuid());
        if (optionalFlight.isEmpty()) {
            log.info(String.format("There was no flight of uuid: %s to remove", command.getUuid().toString()));
            return;
        }
        Flight flightToRemove = optionalFlight.get();
        flightRepository.delete(flightToRemove);
        log.info(String.format("flight of uuid: %s was successfully removed", command.getUuid().toString()));
        FlightRemovedEvent event = (FlightRemovedEvent) FlightChangedEvent.builder()
                .uuid(UUID.randomUUID())
                .timeStamp(LocalDateTime.now())
                .flightUuid(flightToRemove.getId())
                .arrivalAirport(flightToRemove.getArrivalAirport())
                .departureAirport(flightToRemove.getDepartureAirport())
                .arrivalDate(flightToRemove.getArrivalDate().toString())
                .departureDate(flightToRemove.getDepartureDate().toString())
                .build();
        log.info(String.format("Generated event: %s", event.toString()));
        flightChangedEventRepository.save(event);
        rabbitTemplate.convertAndSend(flightRemovedEventQueue.getName(), event);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.ChangeFlightPriceQueue}")
    public void changeFlightPrice(ChangeFlightPriceCommand command) {
        Optional<Flight> optionalFlight = flightRepository.findById(command.getUuid());
        if (optionalFlight.isEmpty()) {
            log.info(String.format("There was no flight of uuid: %s to change", command.getUuid().toString()));
            return;
        }
        Flight flightToChange = optionalFlight.get();
        int oldPrice = flightToChange.getPrice();
        flightToChange.setPrice((int) command.getChangedPrice());
        flightRepository.save(flightToChange);
        log.info(String.format("flight of uuid: %s price was successfully changed to %s", command.getUuid().toString(), command.getChangedPrice()));
        FlightPriceChangedEvent event = (FlightPriceChangedEvent) FlightChangedEvent.builder()
                .uuid(UUID.randomUUID())
                .timeStamp(LocalDateTime.now())
                .flightUuid(flightToChange.getId())
                .arrivalAirport(flightToChange.getArrivalAirport())
                .departureAirport(flightToChange.getDepartureAirport())
                .arrivalDate(flightToChange.getArrivalDate().toString())
                .departureDate(flightToChange.getDepartureDate().toString())
                .build();
        event.setOldPrice(oldPrice);
        event.setNewPrice(flightToChange.getPrice());
        flightChangedEventRepository.save(event);
        rabbitTemplate.convertAndSend(flightPriceChangedEventQueue.getName(), event);
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue.GetFlightChangeEventsQueue}")
    public GetLastFlightChangesResponse getLastFlightChanges(GetLastFlightChangesRequest request) {
        List<FlightChangedEvent> flightChangedEvents = flightChangedEventRepository.findAll().stream()
                .sorted(Comparator.comparing(FlightChangedEvent::getTimeStamp)) // maybe .reversed()
                .limit(10)
                .toList();
        return new GetLastFlightChangesResponse(flightChangedEvents);
    }

}


