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
public class Trip {
    @Id
    private String tripId;
    private String uuid;
    private String hotelId;
    private String fromFlightId;
    private String toFlightId;
    // TODO: start date i end date
    private Float tripPrice;
    private String dateStart;
    private String dateEnd;
}