package com.example.transportation;

import com.example.transportation.command.BookFlightCommand;
import com.example.transportation.command.CancelFlightReservationCommand;
import com.example.transportation.event.domain.FlightAddedEvent;
import com.example.transportation.event.domain.FlightBookedEvent;
import com.example.transportation.event.domain.FlightCancelledEvent;
import com.example.transportation.event.repo.FlightAddedEventRepository;
import com.example.transportation.event.repo.FlightBookedEventRepository;
import com.example.transportation.event.repo.FlightCancelledEventRepository;
import com.example.transportation.flight.domain.Flight;
import com.example.transportation.flight.domain.FlightReservation;
import com.example.transportation.flight.repo.FlightRepository;
import com.example.transportation.flight.repo.FlightReservationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CancelFlightReservationListener {
    private final FlightRepository flightRepository;

    private final FlightReservationRepository reservationRepository;
    private final FlightCancelledEventRepository eventRepository;

    private final FlightBookedEventRepository bookedFlights;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CancelFlightReservationListener(RabbitTemplate rabbitTemplate, FlightRepository flightRepository,
                                           FlightCancelledEventRepository eventRepository, FlightReservationRepository flightReservationRepository,
                                           FlightBookedEventRepository bookedFlights) {
        this.reservationRepository = flightReservationRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.flightRepository = flightRepository;
        this.eventRepository = eventRepository;
        this.bookedFlights = bookedFlights;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.cancelFlightQueue}")
    public String receiveMessage(Message<CancelFlightReservationCommand> message) {

        FlightReservation reservation = this.reservationRepository.getById(message.getPayload().getReservationID());

        if (reservation != null) {
            Flight flight = reservation.getFlight();
            flight.setSitsOccupied(flight.getSitsOccupied() - 1);
            flightRepository.save(flight);

            FlightBookedEvent reservationEvent = bookedFlights.findByReservationId(reservation.getId());

            reservationRepository.delete(reservation);
            FlightCancelledEvent event = new FlightCancelledEvent(UUID.randomUUID(), reservationEvent);

            eventRepository.save(event);

            return "success";
        }

        return "Failure";
    }
}
