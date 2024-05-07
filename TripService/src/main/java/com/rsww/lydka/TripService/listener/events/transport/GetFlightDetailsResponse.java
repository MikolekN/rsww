package com.rsww.lydka.TripService.listener.events.transport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import com.rsww.lydka.TripService.Entity.Flight;

import java.util.function.Function;

@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class GetFlightDetailsResponse {
    private String flightId;
    private String departureAirport;
    private String arrivalAirport;
    private String departureDate;
    private String arrivalDate;
    private int travelTime;
    private int sitsCount;
    private int sitsOccupied;
    private int price;

    public static Function<GetFlightDetailsResponse, Flight> toEntityMapper() {
        return response -> Flight.builder()
                .flightId(response.getFlightId())
                .departureAirport(response.departureAirport)
                .arrivalAirport(response.arrivalAirport)
                .arrivalDate(response.arrivalDate)
                .departureDate(response.departureDate)
                .travelTime(response.travelTime)
                .sitsCount(response.sitsCount)
                .sitsOccupied(response.sitsOccupied)
                .price(response.price)
                .build();
    }
}
