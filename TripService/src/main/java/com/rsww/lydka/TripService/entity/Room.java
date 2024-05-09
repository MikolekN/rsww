package com.rsww.lydka.TripService.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("trips")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    @Id
    private String roomId;
    private String uuid;
    private int numberOfAdults;
    private int numberOfChildren;
    private String type;
    private float price;
    private String hotelUuid;
}
