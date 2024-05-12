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
public class Trip {
    @Id
    private String tripId;
    private String uuid;
    private String hotelId;
    private String fromFlightId;
    private String toFlightId;
    private Float tripPrice;
    private String dateStart;
    private String dateEnd;
}
