package com.rsww.lydka.TripService.listener.events.trip.reservation.accommodation;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ReserveHotelCommand {
    private String hotelId;
    private Room room;
    private String user;
    private String startDate;
    private String endDate;

    @Data
    @Builder
    @Jacksonized
    public static class Room {
        private int numberOfAdults;
        private int numberOfChildren;
        private String type;
        private float price;
    }
}
