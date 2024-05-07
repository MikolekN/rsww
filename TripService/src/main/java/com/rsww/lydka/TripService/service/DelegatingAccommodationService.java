package com.rsww.lydka.TripService.service;

//import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import com.rsww.lydka.TripService.Entity.Hotel;
import com.rsww.lydka.TripService.listener.events.accommodation.GetHotelRequest;
import com.rsww.lydka.TripService.listener.events.accommodation.GetHotelsRequest;
import com.rsww.lydka.TripService.listener.events.accommodation.GetHotelsResponse;
import com.rsww.lydka.TripService.listener.events.accommodation.HotelDetailsResponse;
import com.rsww.lydka.TripService.listener.events.trip.reservation.PostReservationRequest;
import com.rsww.lydka.TripService.listener.events.trip.reservation.accommodation.CancelHotelReservation;
import com.rsww.lydka.TripService.listener.events.trip.reservation.accommodation.ReservationResponse;
import com.rsww.lydka.TripService.listener.events.trip.reservation.accommodation.ReserveHotelRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

@Component
public class DelegatingAccommodationService {
    private final Logger logger = LoggerFactory.getLogger(DelegatingAccommodationService.class);
    private final String allHotelsQueueName;
    private final String getHotelDetailsQueueName;
    private final String reserveHotelQueueName;
    private final String cancelHotelReservationQueueName;
    private final AsyncRabbitTemplate rabbitTemplate;

    @Autowired
    public DelegatingAccommodationService(final AsyncRabbitTemplate rabbitTemplate,
                                  @Value("${spring.rabbitmq.queue.hotelAllRequestQueue}") final String allHotelsQueueName,
                                  @Value("${spring.rabbitmq.queue.hotelInfoRequestQueue}") final String getHotelDetailsQueueName,
                                  @Value("${spring.rabbitmq.queue.reservationMakeQueue}") final String reserveHotelQueueName,
                                  @Value("${spring.rabbitmq.queue.reservationCancelQueue}") final String cancelHotelReservationQueueName) {
        this.rabbitTemplate = rabbitTemplate;
        this.allHotelsQueueName = allHotelsQueueName;
        this.getHotelDetailsQueueName = getHotelDetailsQueueName;
        this.reserveHotelQueueName = reserveHotelQueueName;
        this.cancelHotelReservationQueueName = cancelHotelReservationQueueName;
    }

    public List<Hotel> getHotels(SearchParams params) {
        final var request = SearchParams.toRequest().apply(params);
        final CompletableFuture<GetHotelsResponse> completableRequest = rabbitTemplate.convertSendAndReceiveAsType(
                allHotelsQueueName,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        try {
            final var response = completableRequest.get();
            final var hotels = GetHotelsResponse.dtoToEntityMapper().apply(response);
            logger.debug("Fetched {} hotels.", hotels.size());
            return hotels;
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Cannot get all hotels from hotel service.", e);
        }
        return List.of(); // TODO: tutaj mam zwykła liste bo ImmutableList import nie dzialal
    }

    public Optional<Hotel> getHotel(String id) {
        final var request = GetHotelRequest.builder().hotelid(id).build();
        final CompletableFuture<HotelDetailsResponse> completableRequest = rabbitTemplate.convertSendAndReceiveAsType(
                getHotelDetailsQueueName,
                request,
                new ParameterizedTypeReference<>() {
                }
        );

        try {
            final var response = completableRequest.get();
            final var hotel = HotelDetailsResponse.dtoToEntityMapper().apply(response);
            logger.debug("Fetched hotel {}", hotel);
            return Optional.of(hotel);
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error when fetching hotel {} details.", id, e);
        }
        return Optional.empty();
    }

    public ReservationResponse reserve(Hotel hotel,
                                       PostReservationRequest.Room room,
                                       final String startDate,
                                       final String endDate,
                                       final String user) {
        final var request = ReserveHotelRequest.builder()
                .hotelId(hotel.getHotelId())
                .startDate(startDate)
                .endDate(endDate)
                .user(user)
                .room(ReserveHotelRequest.Room.builder()
                        .numberOfAdults(room.getNumberOfAdults())
                        .numberOfChildren(room.getNumberOfChildren())
                        .type(room.getType())
                        .build())
                .build();
        final CompletableFuture<ReservationResponse> completableRequest = rabbitTemplate.convertSendAndReceiveAsType(
                reserveHotelQueueName,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        try {
            final var response = completableRequest.get();
            logger.debug("Reservation response {}", response);
            return response;
        } catch (ExecutionException | InterruptedException e) {
            logger.error("Error when reserving hotel {}\nRoom: {}.", hotel, room, e);
        }
        return ReservationResponse.builder().success(false).build();
    }

    public boolean cancelReservation(String reservationId) {
        final var dto = CancelHotelReservation.builder().reservationId(reservationId).build();
        rabbitTemplate.convertSendAndReceiveAsType(
                cancelHotelReservationQueueName,
                dto,
                new ParameterizedTypeReference<>() {
                }
        );
        return true;
    }

    @Data
    @Builder
    public static class SearchParams {
        private String destination;
        private String departure;
        private Integer adults;
        private Integer people3To9;
        private Integer people10To17;

        public static Function<SearchParams, GetHotelsRequest> toRequest() {
            return params -> GetHotelsRequest.builder()
                    .destination(params.destination)
                    .adults(params.adults)
                    .people3To9(params.people3To9)
                    .people10To17(params.people10To17)
                    .build();
        }
    }
}
