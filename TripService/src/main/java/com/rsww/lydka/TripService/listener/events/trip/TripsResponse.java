package com.rsww.lydka.TripService.listener.events.trip;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import com.rsww.lydka.TripService.Entity.Flight;
import com.rsww.lydka.TripService.listener.events.accommodation.HotelDetailsResponse;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Data
@Builder
@Jacksonized
public class TripsResponse {
    private List<Trip> trips;

    @Data
    @Builder
    @Jacksonized
    public static class Trip {
        private String tripId;
        private Hotel hotel;
        private Double tripPrice;
        private String dateStart;
        private String dateEnd;
    }

    @Data
    @Builder
    @Jacksonized
    public static class Hotel {
        private String hotelId;
        private String name;
        private int stars;
        private String country;
    }

    public static Function<com.rsww.lydka.TripService.Entity.Trip, Trip> toDtoMapper(final Function<Long, Optional<Flight>> transportAccessor,
                                                                        final Function<Long, Optional<com.rsww.lydka.TripService.Entity.Hotel>> hotelAccessor) {
        return trip -> {
            final var tripBuilder = Trip.builder();
            final var maybeHotel = hotelAccessor.apply(Long.valueOf(trip.getHotelId())); // nie wiem czy nie trzeba bedzie zmienic id na long
            double basePrice = 0L;

            if (maybeHotel.isPresent()) {
                final var hotel = maybeHotel.get();
                tripBuilder.hotel(Hotel.builder().hotelId(hotel.getHotelId())
                        .name(hotel.getName())
                        .country(hotel.getCountry())
                        .stars(hotel.getStars())
                        .build());
                basePrice = hotel.getRooms().stream()
                        .mapToDouble(com.rsww.lydka.TripService.Entity.Room::getPrice)
                        .min()
                        .getAsDouble();
            }
            tripBuilder.tripId(trip.getTripId());

            final var startTransport = transportAccessor.apply(Long.valueOf(trip.getFromFlightId()));
            final var endTransport = transportAccessor.apply(Long.valueOf(trip.getToFlightId()));
            if (startTransport.isPresent()) {
                final var transport = startTransport.get();
                tripBuilder.dateStart(transport.getDepartureDate());
                basePrice = basePrice + transport.getPrice();
            }
            if (endTransport.isPresent()) {
                final var transport = endTransport.get();
                tripBuilder.dateEnd(transport.getDepartureDate());
                basePrice = basePrice + transport.getPrice();
            }

            tripBuilder.tripPrice(basePrice);
            return tripBuilder.build();
        };
    }
}
