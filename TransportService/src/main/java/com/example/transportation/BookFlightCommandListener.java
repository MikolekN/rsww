package com.example.transportation;

import com.example.transportation.Entity.Flight;
import com.example.transportation.Entity.FlightReservation;
import com.example.transportation.Repository.FlightRepository;
import com.example.transportation.Repository.FlightReservationRepository;
import com.example.transportation.command.AddFlightCommand;
import com.example.transportation.command.BookFlightCommand;
import org.springframework.amqp.core.Queue;
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
    private final Queue confirmFlightReservationQueue;

    @Autowired
    public BookFlightCommandListener(FlightReservationRepository repository, RabbitTemplate rabbitTemplate,
                                     Queue confirmFlightReservationQueue, FlightRepository flightRepository) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
        this.confirmFlightReservationQueue = confirmFlightReservationQueue;
        this.flightRepository = flightRepository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.reserveFlightQueue}")
    public void receiveMessage(Message<BookFlightCommand> message) {
        BookFlightCommand command = message.getPayload();
        Flight reservationFlight = flightRepository.getById(command.getFlightId());

        if (reservationFlight.hasAvailableSits(command.getNumberOfPeople())) {
            FlightReservation reservation = BookFlightCommand.commandToEntityMapper(command, reservationFlight);
            FlightReservation savedReservation = repository.save(reservation);

            reservationFlight.setSitsOccupied(reservationFlight.getSitsOccupied() + 1);
            flightRepository.save(reservationFlight);

            rabbitTemplate.convertAndSend(confirmFlightReservationQueue.getName(), savedReservation);
        }
    }
}
