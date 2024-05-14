package com.example.transportation.command;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CancelFlightReservationCommand {
    UUID reservationID;
}
