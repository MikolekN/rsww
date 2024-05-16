package com.rsww.lydka.TripService.listener.events.trip.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class HotelReservationResponseInfo {
    @JsonProperty("timestamp")
    private LocalDateTime timeStamp;
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;
    @JsonProperty("number_of_adults")
    private int numberOfAdults;
    @JsonProperty("number_of_children_under_10")
    private int numberOfChildrenUnder10;
    @JsonProperty("number_of_children_under_18")
    private int numberOfChildrenUnder18;
    @JsonProperty("hotel")
    private UUID hotel;
    @JsonProperty("room")
    private UUID room;
    @JsonProperty("reservation_uuid")
    private UUID reservationUuid;
    @JsonProperty("event_type")
    private int eventType;

    public HotelReservationResponseInfo(UUID uuid, UUID reservationUuid,
                                LocalDateTime timeStamp,
                                LocalDate startDate, LocalDate endDate,
                                int numberOfAdults, int numberOfChildrenUnder10, int numberOfChildrenUnder18,
                                UUID hotel, UUID room
    ) {
        super(uuid, reservationUuid);
        this.timeStamp = timeStamp;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildrenUnder10 = numberOfChildrenUnder10;
        this.numberOfChildrenUnder18 = numberOfChildrenUnder18;
        this.hotel = hotel;
        this.room = room;
        this.setEventType(1);
    }
}
