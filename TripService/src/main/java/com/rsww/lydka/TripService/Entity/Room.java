package com.rsww.lydka.TripService.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

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
