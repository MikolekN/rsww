package com.example.transportation.command;

import com.example.transportation.Entity.Flight;
import lombok.Getter;

import java.util.UUID;

@Getter
public class AddFlightCommand {
    private UUID commandId;
    private String departureAirport;
    private String arrivalAirport;
    private String departureDate;
    private String arrivalDate;
    private int travelTime;
    private int sitsCount;
    private int price;

    public static Flight commandToEntityMapper(AddFlightCommand command) {
        return new Flight(command.getDepartureAirport(), command.getArrivalAirport(),
                command.getDepartureDate(), command.getArrivalDate(),
                command.getTravelTime(), command.getSitsCount(), command.getPrice());
    }
}
