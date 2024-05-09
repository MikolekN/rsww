package com.rsww.lydka.TripService.listener.events.trip;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.rsww.lydka.TripService.entity.Room;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import com.rsww.lydka.TripService.entity.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Data
@Builder
@Jacksonized
public class TripDetailsResponse {
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private Hotel hotel;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private Flight flight;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private Flight returnFlight;

    @Data
    @Builder
    @Jacksonized
    public static class Hotel {
        private String hotelId;
        private String uuid;
        private String name;
        private String country;
        private int stars;
        private ArrayList<Room> rooms;

        @Data
        @Builder
        @Jacksonized
        public static class Room {
            private int numberOfAdults;
            private int numberOfChildren;
            private String type;
            private float price;
        }
    }

    @Data
    @Builder
    @Jacksonized
    public static class Flight {
        private String departureAirport;
        private String arrivalAirport;
        private String departureDate;
        private String arrivalDate;
        private int travelTime;
        private int sitsCount;
        private int sitsOccupied;
        private int price;
    }

    public static Function<Trip, TripDetailsResponse> toDtoMapper(Supplier<Optional<com.rsww.lydka.TripService.entity.Hotel>> hotelSupplier,
                                                                  Function<String, Optional<com.rsww.lydka.TripService.entity.Flight>> transportAccessor) {
        return trip -> {
            final var maybeHotel = hotelSupplier.get();
            final var maybeFlight = transportAccessor.apply(trip.getFromFlightId());
            final var maybeReturnFlight = transportAccessor.apply(trip.getToFlightId());

            final var tripDetailsBuilder = TripDetailsResponse.builder();
            if (maybeFlight.isPresent()) {
                final var flight = maybeFlight.get();
                tripDetailsBuilder.flight(Flight.builder()
                        .departureAirport(flight.getDepartureAirport())
                        .arrivalAirport(flight.getArrivalAirport())
                        .departureDate(flight.getDepartureDate())
                        .arrivalDate(flight.getArrivalDate())
                        .travelTime(flight.getTravelTime())
                        .sitsCount(flight.getSitsCount())
                        .sitsOccupied(flight.getSitsOccupied())
                        .build());
            }

            if (maybeReturnFlight.isPresent()) {
                final var flight = maybeReturnFlight.get();
                tripDetailsBuilder.flight(Flight.builder()
                        .departureAirport(flight.getDepartureAirport())
                        .arrivalAirport(flight.getArrivalAirport())
                        .departureDate(flight.getDepartureDate())
                        .arrivalDate(flight.getArrivalDate())
                        .travelTime(flight.getTravelTime())
                        .sitsCount(flight.getSitsCount())
                        .sitsOccupied(flight.getSitsOccupied())
                        .build());
            }

            if (maybeHotel.isPresent()) {
                final var hotel = maybeHotel.get();
                tripDetailsBuilder.hotel(Hotel.builder()
                        .hotelId(hotel.getHotelId())
                        .name(hotel.getName())
                        .country(hotel.getCountry())
                        .stars(hotel.getStars())
                        .rooms((ArrayList<Hotel.Room>) hotel.getRooms().stream().map(room -> Hotel.Room.builder()
                                .numberOfAdults(room.getNumberOfAdults())
                                .numberOfChildren(room.getNumberOfChildren())
                                .type(room.getType())
                                .price(room.getPrice())
                                .build()).collect(Collectors.toList()))
                        .build());
            }
            return tripDetailsBuilder.build();
        };
    }

//    private static String extractCity(String place) {
//        final var tokens = place.split("/");
//        if (tokens.length > 1) {
//            return tokens[1].trim();
//        } else return "";
//    }
//
//    private static String extractCountry(String place) {
//        final var tokens = place.split("/");
//        if (tokens.length > 0) {
//            return tokens[0].trim();
//        } else return "";
//    }
}
