package com.rsww.lydka.TripService.listener.events.accommodation.event;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Event implements Serializable {
    private UUID uuid;
    private LocalDateTime timeStamp;
}
