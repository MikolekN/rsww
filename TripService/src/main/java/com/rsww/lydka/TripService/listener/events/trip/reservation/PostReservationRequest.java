package com.rsww.lydka.TripService.listener.events.trip.reservation;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class PostReservationRequest {
    private String tripId;
    private Room room;
    private String user;

    @Data
    @Builder
    @Jacksonized
    public static class Room {
        private int numberOfAdults;
        private int numberOfChildren;
        private String type;
        private float price;
        private Long key; // moze bedzie zmiana na String
    }
}