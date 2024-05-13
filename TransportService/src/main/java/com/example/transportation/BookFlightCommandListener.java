package com.example.transportation;

import com.example.transportation.flight.domain.Flight;
import com.example.transportation.flight.domain.FlightReservation;
import com.example.transportation.flight.repo.FlightRepository;
import com.example.transportation.flight.repo.FlightReservationRepository;
import com.example.transportation.command.BookFlightCommand;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class BookFlightCommandListener {
    private final FlightReservationRepository repository;
    private final FlightRepository flightRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public BookFlightCommandListener(FlightReservationRepository repository, RabbitTemplate rabbitTemplate,
                                     FlightRepository flightRepository) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
        this.flightRepository = flightRepository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.reserveFlightQueue}")
    public FlightReservation receiveMessage(Message<BookFlightCommand> message) {
        BookFlightCommand command = message.getPayload();
        Flight reservationFlight = flightRepository.getById(command.getFlightId());

        if (reservationFlight.hasAvailableSits(command.getPeopleCount())) {
            FlightReservation reservation = BookFlightCommand.commandToEntityMapper(command, reservationFlight);
            FlightReservation savedReservation = repository.save(reservation);

            reservationFlight.setSitsOccupied(reservationFlight.getSitsOccupied() + 1);
            flightRepository.save(reservationFlight);

            return savedReservation;
        }

        return null;
    }
}
