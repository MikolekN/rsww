package com.rsww.lydka.TripService.listener;

import com.rsww.lydka.TripService.listener.events.orders.GetAllOrdersRequest;
import com.rsww.lydka.TripService.listener.events.orders.GetAllOrdersResponse;
import com.rsww.lydka.TripService.listener.events.payment.PayForReservationCommand;
import com.rsww.lydka.TripService.listener.events.payment.PaymentResponse;
import com.rsww.lydka.TripService.listener.events.trip.TripDetailsRequest;
import com.rsww.lydka.TripService.listener.events.trip.TripDetailsResponse;
import com.rsww.lydka.TripService.listener.events.trip.reservation.PostReservationRequest;
import com.rsww.lydka.TripService.listener.events.trip.reservation.PostReservationResponse;
import com.rsww.lydka.TripService.listener.events.trip.reservation.UserReservationsRequest;
import com.rsww.lydka.TripService.service.DelegatingAccommodationService;
import com.rsww.lydka.TripService.service.DelegatingTransportService;
import com.rsww.lydka.TripService.service.PaymentService;
import com.rsww.lydka.TripService.service.TripService;
import com.rsww.lydka.TripService.listener.events.trip.TripsResponse;
import com.rsww.lydka.TripService.listener.events.trip.TripsRequest;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;

import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TripEventsListener {
    private final static Logger logger = LoggerFactory.getLogger(TripEventsListener.class);
    private final TripService tripService;
    private final DelegatingAccommodationService accommodationService;
    private final DelegatingTransportService transportService;
    private final PaymentService paymentService;

    @Autowired
    public TripEventsListener(final TripService tripService,
                              final DelegatingAccommodationService accommodationService,
                              final DelegatingTransportService transportService,
                              final PaymentService paymentService) {
        this.tripService = tripService;
        this.accommodationService = accommodationService;
        this.transportService = transportService;
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.getTripDetails}")
    public TripDetailsResponse getTripDetails(TripDetailsRequest request) {
        final var foundTrip = tripService.getTripById(UUID.fromString(request.getTripId()));
        if (foundTrip.isEmpty()) {
            return TripDetailsResponse.builder().build();
        }
        final var trip = foundTrip.get();
        return TripDetailsResponse.toDtoMapper(
                () -> accommodationService.getHotel(trip.getHotelId()),
                transportService::getTransport
        ).apply(trip);
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.getTrips}")
    public TripsResponse getTrips(TripsRequest request) {
        logger.debug("Request: {}", request);

        final var trips = tripService.getTrips(request).stream().collect(Collectors.toList());
        final var dtoTrips = trips.parallelStream().limit(200).map(trip -> TripsResponse.toDtoMapper(
                                transportService::getTransport,
                                accommodationService::getHotel)
                        .apply(trip))
                .collect(Collectors.toList());
        final var response = TripsResponse.builder().trips(dtoTrips).build();
        logger.debug("Response: {}", response);
        return response;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.reserveTrip}")
    public PostReservationResponse reserveTrip(PostReservationRequest request) {
        String requestNumber = "[" + Integer.toHexString(new Random().nextInt(0xFFFF)) + "]";
        logger.info("{} Received a reservation request.", requestNumber);

        final var reservationResult = tripService.reserveTrip(request, requestNumber);
        if (reservationResult == null) {
            return new PostReservationResponse(null, false, null);
        }
        logger.info("{} {} reservation.", requestNumber, (reservationResult.isResponse() ? "Successful" : "Unsuccessful"));
        return reservationResult;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.getReservations}")
    public TripsResponse reservations(UserReservationsRequest request) {
        final var user = request.getUserId();
        final var trips = tripService.getReservations(Long.valueOf(user));
        return TripsResponse.builder()
                .trips(trips)
                .build();
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.tripReservationPayment}")
    public PaymentResponse payForReservation(PayForReservationCommand request) {
        String requestNumber = "[" + Integer.toHexString(new Random().nextInt(0xFFFF)) + "]";
        logger.info("{} Received a payment request.", requestNumber);

        final var responseFromPaymentService = paymentService.paymentRequest(request);
        if (responseFromPaymentService == null) {
            return new PaymentResponse(request.getUuid(), false, request.getReservationId());
        }
        logger.info("{} {} payment.", requestNumber, (responseFromPaymentService.isResponse() ? "Successful" : "Unsuccessful"));
        if (responseFromPaymentService.isResponse()) {
            tripService.confirmReservation(UUID.fromString(request.getReservationId()));
        }
        return responseFromPaymentService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.ordersQueue}")
    public GetAllOrdersResponse getAllOrders(GetAllOrdersRequest request) {
        logger.info("Received a GetAllOrdersRequest {}", request);
        GetAllOrdersResponse response = tripService.getAllOrders(request);
        logger.info("Received a GetAllOrdersResponse {}", response);
        return response;
    }
}