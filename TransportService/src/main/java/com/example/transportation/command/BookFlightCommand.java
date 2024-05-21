package com.example.transportation.command;

import com.example.transportation.flight.domain.Flight;
import com.example.transportation.flight.domain.FlightReservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookFlightCommand {
    private UUID flightId;
    private long userId;
    private int peopleCount;

    public static FlightReservation commandToEntityMapper(BookFlightCommand command, Flight flight) {
        return new FlightReservation(true, flight, command.getUserId(), command.getPeopleCount());
    }
}
