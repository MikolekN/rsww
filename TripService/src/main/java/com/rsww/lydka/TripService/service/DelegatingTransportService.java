package com.rsww.lydka.TripService.service;

//import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import com.rsww.lydka.TripService.entity.Flight;
import com.rsww.lydka.TripService.listener.events.transport.GetFlightDetailsQuery;
import com.rsww.lydka.TripService.listener.events.transport.GetFlightDetailsResponse;
import com.rsww.lydka.TripService.listener.events.transport.GetFlightsQueryRequest;
import com.rsww.lydka.TripService.listener.events.trip.reservation.transport.CancelFlightReservationCommand;
import com.rsww.lydka.TripService.listener.events.trip.reservation.transport.ReservationResponse;
import com.rsww.lydka.TripService.listener.events.trip.reservation.transport.ReserveFlightCommand;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class DelegatingTransportService {
    private final Logger logger = LoggerFactory.getLogger(DelegatingTransportService.class);
    private final String flightDetailsQueue;
    private final String flightsWithQueryQueue;
    private final String reserveFlight;
    private final String confirmFlight;
    private final String cancelFlight;
    private final AsyncRabbitTemplate template;

    @Autowired
    public DelegatingTransportService(final AsyncRabbitTemplate asyncRabbitTemplate,
                                      @Value("${spring.rabbitmq.queue.getFlightDetailsQueue}") final String flightDetailsQueue,
                                      @Value("${spring.rabbitmq.queue.flightsWithQueryQueue}") final String flightsWithQueryQueue,
                                      @Value("${spring.rabbitmq.queue.reserveFlightQueue}") final String reserveFlight,
                                      @Value("${spring.rabbitmq.queue.confirmFlightReservationQueue}") final String confirmFlight,
                                      @Value("${spring.rabbitmq.queue.cancelFlightReservationQueue}") final String cancelFlight) {
        this.template = asyncRabbitTemplate;
        this.flightDetailsQueue = flightDetailsQueue;
        this.flightsWithQueryQueue = flightsWithQueryQueue;
        this.reserveFlight = reserveFlight;
        this.confirmFlight = confirmFlight;
        this.cancelFlight = cancelFlight;
    }

    public List<Flight> getTransports() {
        return List.of();
    }

    public Optional<Flight> getTransport(String id) {
        Flight flight = null;
        final var request = GetFlightDetailsQuery.builder().flightId(id).build();
        final CompletableFuture<GetFlightDetailsResponse> completableRequest = template.convertSendAndReceiveAsType(
                flightDetailsQueue,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        try {
            final var response = completableRequest.get();
            flight = GetFlightDetailsResponse.toEntityMapper().apply(response);
        }  catch (ExecutionException | InterruptedException e) {
            logger.error("Error when fetching hotel {} details.", id, e);
        }
        return Optional.ofNullable(flight);
    }

    public List<Flight> getTransports(final String departureAirport,
                                         final String arrivalAirport,
                                         final String departureDate,
                                         final String arrivalDate) {
        GetFlightsQueryRequest query = GetFlightsQueryRequest.builder()
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .departureDate(departureDate)
                .arrivalDate(arrivalDate)
                .build();

        CompletableFuture<List<GetFlightDetailsResponse>> completableRequest = template.convertSendAndReceiveAsType(
                flightsWithQueryQueue,
                query,
                new ParameterizedTypeReference<>() {
                });
        try {
            return completableRequest.get().stream().map(dto -> Flight.builder()
                            .departureAirport(dto.getDepartureAirport())
                            .arrivalAirport(dto.getArrivalAirport())
                            .arrivalDate(dto.getArrivalDate())
                            .departureDate(dto.getDepartureDate())
                            .travelTime(dto.getTravelTime())
                            .sitsCount(dto.getSitsCount())
                            .sitsOccupied(dto.getSitsOccupied())
                            .price(dto.getPrice())
                            .flightId(dto.getFlightId())
                            .build())
                    .collect(Collectors.toList());
        }  catch (ExecutionException | InterruptedException e) {
            logger.error("Error when fetching transports with query parameters departureAirport={};" +
                            " arrivalAirport={}; departureDate={}; arrivalDate={}",
                    departureAirport,
                    arrivalAirport,
                    departureAirport,
                    arrivalAirport,
                    e
            );
        }
        return List.of();
    }

    public Optional<Long> reserve(String flightId, String user, int numberOfPeople) {
        final var dto = ReserveFlightCommand.builder()
                .userId(user)
                .flightId(flightId)
                .numberOfPeople(numberOfPeople)
                .build();

        final CompletableFuture<ReservationResponse> completableRequest = template.convertSendAndReceiveAsType(
                reserveFlight,
                dto,
                new ParameterizedTypeReference<>() {
                }
        );

        try {
            final var response = completableRequest.get();
            if (response.isStatus()) {
                return Optional.of(Long.parseLong(response.getMessage().split(":")[1].trim()));
            }
            return Optional.empty();
        }  catch (ExecutionException | InterruptedException e) {
            logger.error("Cannot reserve the flight with id: {}", flightId, e);
        }
        return Optional.empty();
    }

    public boolean cancelReservation(Long reservationId) {
        final var dto = CancelFlightReservationCommand.builder().reservationId(reservationId).build();
        final CompletableFuture<ReservationResponse> completableRequest = template.convertSendAndReceiveAsType(
                cancelFlight,
                dto,
                new ParameterizedTypeReference<>() {
                }
        );

        return true;
    }

}
