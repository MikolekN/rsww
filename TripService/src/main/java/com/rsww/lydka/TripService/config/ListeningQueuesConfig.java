package com.rsww.lydka.TripService.config;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

// TODO: sprawdzic czy wszystkie potrzebne kolejki sa i usunac te niepotrzebne
public class ListeningQueuesConfig {
    @Bean(name = "reserveHotelQueue")
    Queue reserveHotelQueue(@Value("${spring.rabbitmq.queue.hotel.reservation}") final String reserveHotelQueue) {
        return new Queue(reserveHotelQueue, true);
    }

    @Bean(name = "cancelHotelReservationQueue")
    Queue cancelHotelReservationQueue(@Value("${spring.rabbitmq.queue.hotel.reservation.cancel}") final String cancelHotelReservationQueue) {
        return new Queue(cancelHotelReservationQueue, true);
    }

    @Bean(name = "confirmHotelReservationQueue")
    Queue confirmHotelReservationQueue(@Value("${spring.rabbitmq.queue.hotel.reservation.confirm}") final String confirmHotelReservationQueue) {
        return new Queue(confirmHotelReservationQueue, true);
    }

    @Bean(name = "addHotelQueue")
    Queue addHotelQueue(@Value("${spring.rabbitmq.queue.hotelCreateQueue}") final String addHotelQueue) {
        return new Queue(addHotelQueue, true);
    }

    @Bean(name = "deleteHotelQueue")
    Queue deleteHotelQueue(@Value("${spring.rabbitmq.queue.hotel.delete}") final String deleteHotelQueue) {
        return new Queue(deleteHotelQueue, true);
    }

    @Bean(name = "addRoomQueue")
    Queue addRoomQueue(@Value("${spring.rabbitmq.queue.roomCreateQueue}") final String addRoomQueue) {
        return new Queue(addRoomQueue, true);
    }

    @Bean(name = "deleteRoomQueue")
    Queue deleteRoomQueue(@Value("${spring.rabbitmq.queue.hotel.room.delete}") final String deleteRoomQueue) {
        return new Queue(deleteRoomQueue, true);
    }

    @Bean
    public Queue getHotelsQueue(@Value("${spring.rabbitmq.queue.hotelAllRequestQueue}") String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    public Queue getHotelDetailsQueue(@Value("${spring.rabbitmq.queue.hotelInfoRequestQueue}") String queueName) {
        return new Queue(queueName, true);
    }

//    @Bean(name = "autoDeleteQueue")
//    Queue autoDeleteQueue() {
//        return new AnonymousQueue();
//    }

    @Bean
    public Queue getTripsQueue(@Value("${spring.rabbitmq.queue.getTrips}") final String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    public Queue getTripDetailsQueue(@Value("${spring.rabbitmq.queue.trip.get.details}") final String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    public Queue addTripQueue(@Value("${spring.rabbitmq.queue.addTrip}") final String addTripQueueName) {
        return new Queue(addTripQueueName, true);
    }

    @Bean
    public Queue removeTripQueue(@Value("${spring.rabbitmq.queue.removeTrip}") final String removeTripQueueName) {
        return new Queue(removeTripQueueName, true);
    }

    @Bean
    public Queue reserveTrip(@Value("${spring.rabbitmq.queue.reserveTrip}") final String reserveTrip) {
        return new Queue(reserveTrip, true);
    }

    @Bean
    public Queue getReservations(@Value("${spring.rabbitmq.queue.getReservations}") final String name) {
        return new Queue(name, true);
    }
    @Bean
    public Queue tripReservationPayment(@Value("${spring.rabbitmq.queue.tripReservationPayment}") final String name) {
        return new Queue(name, true);
    }
}
