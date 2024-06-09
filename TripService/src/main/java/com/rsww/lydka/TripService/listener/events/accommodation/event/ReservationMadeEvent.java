package com.rsww.lydka.TripService.listener.events.accommodation.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationMadeEvent extends ReservationEvent {
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
    @JsonProperty("room_price")
    private float roomPrice;
    public ReservationMadeEvent(UUID uuid, UUID reservationUuid,
                                LocalDateTime timeStamp,
                                LocalDate startDate, LocalDate endDate,
                                int numberOfAdults, int numberOfChildrenUnder10, int numberOfChildrenUnder18,
                                UUID hotel, UUID room, float roomPrice
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
        this.roomPrice = roomPrice;
        this.setEventType(1);
    }
}
