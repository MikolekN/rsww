package com.rsww.lydka.TripService.listener.events.accommodation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class GetHotelsResponse {

    private List<Hotel> hotels;

    @Data
    @Builder
    @Jacksonized
    public static class Hotel {
        private String hotelId;
        private String uuid;
        private String name;
        private String country;
        private int stars;
    }

    public static Function<GetHotelsResponse, List<com.rsww.lydka.TripService.entity.Hotel>> dtoToEntityMapper() {
        return response -> response.getHotels().stream()
                .map(hotelResponse -> com.rsww.lydka.TripService.entity.Hotel.builder()
                        .hotelId(hotelResponse.getHotelId())
                        .name(hotelResponse.getName())
                        .country(hotelResponse.getCountry())
                        .stars(hotelResponse.getStars())
                        .build())
                .collect(Collectors.toList());
    }
}
