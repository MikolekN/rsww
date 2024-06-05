package com.example.transportation.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CancelFlightReservationCommand {
    private UUID reservationID;
    private int peopleCount;

}
