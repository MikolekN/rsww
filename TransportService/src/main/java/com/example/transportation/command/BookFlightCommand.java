package com.example.transportation.command;

import com.example.transportation.flight.domain.Flight;
import com.example.transportation.flight.domain.FlightReservation;
import lombok.Getter;

import java.util.UUID;

@Getter
public class BookFlightCommand {
    private UUID flightId;
    private long userId;
    private int peopleCount;

    public static FlightReservation commandToEntityMapper(BookFlightCommand command, Flight flight) {
        return new FlightReservation(flight, command.getUserId(), command.getPeopleCount());
    }
}
