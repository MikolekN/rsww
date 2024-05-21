package com.rsww.lydka.TripService.config;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

// TODO: sprawdzic czy wszystkie potrzebne kolejki sa i usunac te niepotrzebne
@Configuration
public class ListeningQueuesConfig {
    @Bean(name = "reserveHotelQueue")
    Queue reserveHotelQueue(@Value("${spring.rabbitmq.queue.reservationMakeQueue}") final String reserveHotelQueue) {
        return new Queue(reserveHotelQueue, true);
    }

    @Bean(name = "cancelHotelReservationQueue")
    Queue cancelHotelReservationQueue(@Value("${spring.rabbitmq.queue.reservationCancelQueue}") final String cancelHotelReservationQueue) {
        return new Queue(cancelHotelReservationQueue, true);
    }

    @Bean(name = "confirmHotelReservationQueue")
    Queue confirmHotelReservationQueue(@Value("${spring.rabbitmq.queue.reservationMadeQueue}") final String confirmHotelReservationQueue) {
        return new Queue(confirmHotelReservationQueue, true);
    }

    @Bean(name = "addHotelQueue")
    Queue addHotelQueue(@Value("${spring.rabbitmq.queue.hotelCreateQueue}") final String addHotelQueue) {
        return new Queue(addHotelQueue, true);
    }

//    @Bean(name = "deleteHotelQueue")
//    Queue deleteHotelQueue(@Value("${spring.rabbitmq.queue.hotel.delete}") final String deleteHotelQueue) {
//        return new Queue(deleteHotelQueue, true);
//    }

    @Bean(name = "addRoomQueue")
    Queue addRoomQueue(@Value("${spring.rabbitmq.queue.roomCreateQueue}") final String addRoomQueue) {
        return new Queue(addRoomQueue, true);
    }

    @Bean(name = "deleteRoomQueue")
    Queue deleteRoomQueue(@Value("${spring.rabbitmq.queue.roomCreateQueue}") final String deleteRoomQueue) {
        return new Queue(deleteRoomQueue, true);
    }

    @Bean(name = "hotelAllRequestQueue")
    public Queue getHotelsQueue(@Value("${spring.rabbitmq.queue.hotelAllRequestQueue}") String queueName) {
        return new Queue(queueName, true);
    }

    @Bean(name = "hotelInfoRequestQueue")
    public Queue getHotelDetailsQueue(@Value("${spring.rabbitmq.queue.hotelInfoRequestQueue}") String queueName) {
        return new Queue(queueName, true);
    }

//    @Bean(name = "autoDeleteQueue")
//    Queue autoDeleteQueue() {
//        return new AnonymousQueue();
//    }

    @Bean(name = "getTrips")
    public Queue getTripsQueue(@Value("${spring.rabbitmq.queue.getTrips}") final String queueName) {
        return new Queue(queueName, true);
    }

    @Bean(name = "getTripDetails")
    public Queue getTripDetailsQueue(@Value("${spring.rabbitmq.queue.getTripDetails}") final String queueName) {
        return new Queue(queueName, true);
    }

    @Bean(name = "addTrip")
    public Queue addTripQueue(@Value("${spring.rabbitmq.queue.addTrip}") final String addTripQueueName) {
        return new Queue(addTripQueueName, true);
    }

    @Bean(name = "removeTrip")
    public Queue removeTripQueue(@Value("${spring.rabbitmq.queue.removeTrip}") final String removeTripQueueName) {
        return new Queue(removeTripQueueName, true);
    }

    @Bean(name = "reserveTrip")
    public Queue reserveTrip(@Value("${spring.rabbitmq.queue.reserveTrip}") final String reserveTrip) {
        return new Queue(reserveTrip, true);
    }

    @Bean(name = "getReservations")
    public Queue getReservations(@Value("${spring.rabbitmq.queue.getReservations}") final String name) {
        return new Queue(name, true);
    }

    @Bean(name = "tripReservationPayment")
    public Queue tripReservationPayment(@Value("${spring.rabbitmq.queue.tripReservationPayment}") final String name) {
        return new Queue(name, true);
    }

    @Bean(name = "requestPaymentQueue")
    public Queue requestPaymentQueue(@Value("${spring.rabbitmq.queue.requestPaymentQueue}") final String name) {
        return new Queue(name, true);
    }

    @Bean(name = "ReserveFlightQueue")
    Queue ReserveFlightQueue(@Value("${spring.rabbitmq.queue.reserveFlightQueue}") final String ReserveFlightQueue) {
        return new Queue(ReserveFlightQueue, true);
    }

    @Bean(name = "CancelFlightReservationQueue")
    Queue CancelFlightReservationQueue(@Value("${spring.rabbitmq.queue.cancelFlightQueue}") final String CancelFlightReservationQueue) {
        return new Queue(CancelFlightReservationQueue, true);
    }
    @Bean(name = "orderInfoQueue")
    Queue orderInfoQueue(@Value("${spring.rabbitmq.queue.orderInfoQueue}") final String orderInfoQueue) {
        return new Queue(orderInfoQueue, true);
    }
}
