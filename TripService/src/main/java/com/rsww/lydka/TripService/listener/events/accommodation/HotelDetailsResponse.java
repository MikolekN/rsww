package com.rsww.lydka.TripService.listener.events.accommodation;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.util.StringUtils;
import com.rsww.lydka.TripService.Entity.Hotel;
import com.rsww.lydka.TripService.Entity.Room;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Function;

@Data
@Builder
@Jacksonized
public class HotelDetailsResponse {
    private long id;
    private String country;
    private String city;
    private int stars;
    private String description;
    private String photo;
    private ArrayList<Room> rooms;
    private String airport;
    private String food;
    private String name;

    public static Function<HotelDetailsResponse, Hotel> dtoToEntityMapper() {
        return response -> Hotel.builder()
                .hotelId(String.valueOf(response.getId()))
                .name(response.getName())
                .country(response.getCountry())
                .stars(response.getStars())
                .rooms(response.getRooms())
                .build();
    }

    private static String getPlaceSafely(final String country, final String city) {
        final var builder = new StringBuilder();
        if (StringUtils.hasText(country)) {
            builder.append(country);
        }
        if (StringUtils.hasText(city)) {
            builder.append(" / ").append(city);
        }
        return builder.toString();
    }
}
