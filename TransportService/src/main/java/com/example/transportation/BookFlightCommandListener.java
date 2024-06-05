package com.example.transportation;

import com.example.transportation.event.domain.FlightAddedEvent;
import com.example.transportation.event.repo.FlightAddedEventRepository;
import com.example.transportation.event.repo.FlightBookedEventRepository;
import com.example.transportation.flight.domain.Flight;
import com.example.transportation.event.domain.FlightBookedEvent;
import com.example.transportation.flight.domain.FlightReservation;
import com.example.transportation.flight.repo.FlightRepository;
import com.example.transportation.flight.repo.FlightReservationRepository;
import com.example.transportation.command.BookFlightCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Component
public class BookFlightCommandListener {
    private final FlightReservationRepository repository;
    private final FlightRepository flightRepository;

    private final FlightBookedEventRepository eventRepository;
    private final FlightAddedEventRepository addedFlightsRepository;
    private final RabbitTemplate rabbitTemplate;
    private final static Logger log = LoggerFactory.getLogger(BookFlightCommandListener.class);


    @Autowired
    public BookFlightCommandListener(FlightReservationRepository repository, RabbitTemplate rabbitTemplate,
                                     FlightRepository flightRepository, FlightBookedEventRepository eventRepository,
                                     FlightAddedEventRepository addedFlightsRepository) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
        this.flightRepository = flightRepository;
        this.eventRepository = eventRepository;
        this.addedFlightsRepository = addedFlightsRepository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.reserveFlightQueue}")
    public FlightReservation receiveMessage(Message<BookFlightCommand> message) {
        BookFlightCommand command = message.getPayload();
        log.info(String.format("Received BookFlightCommand %s", command));
        Optional<Flight> flightOptional = flightRepository.findById(command.getFlightId());
        if (flightOptional.isEmpty()) {
            log.info(String.format("Flight of this uuid: %s was not found", command.getFlightId()));
            return null;
        }
        Flight reservationFlight = flightOptional.get();
        if (reservationFlight.hasAvailableSits(command.getPeopleCount())) {
            FlightReservation reservation = BookFlightCommand.commandToEntityMapper(command, reservationFlight);
            FlightReservation savedReservation = repository.save(reservation);

            reservationFlight.setSitsOccupied(reservationFlight.getSitsOccupied() + command.getPeopleCount());
            flightRepository.save(reservationFlight);

            FlightAddedEvent flightEvent = addedFlightsRepository.findByFlightId(reservationFlight.getId());

            eventRepository.save(new FlightBookedEvent(
                    UUID.randomUUID(),
                    savedReservation.getId(),
                    flightEvent,
                    savedReservation.getUserId(),
                    savedReservation.getPeopleCount()
            ));
            log.info(String.format("Successfully reserved %s", savedReservation));
            return savedReservation;
        }
        log.info(String.format("Flight of this uuid: %s was full", command.getFlightId()));
        return null;
    }
}
