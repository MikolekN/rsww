package com.rsww.mikolekn.APIGateway.offerchange.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OfferChangeRabbitMqConfig {
    @Bean
    public Queue flightRemovedEventFrontQueue(@Value("${spring.rabbitmq.queue.FlightRemovedEventFrontQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue flightPriceChangedEventFrontQueue(@Value("${spring.rabbitmq.queue.FlightPriceChangedEventFrontQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue hotelRemovedEventFrontQueue(@Value("${spring.rabbitmq.queue.HotelRemovedEventFrontQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue roomPriceChangedEventFrontQueue(@Value("${spring.rabbitmq.queue.RoomPriceChangedEventFrontQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue offerChangeFrontQueue(@Value("${spring.rabbitmq.queue.OfferChangeFrontQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getLastOfferChangesQueue(@Value("${spring.rabbitmq.queue.GetLastOfferChangesQueue}") String queue) {
        return new Queue(queue);
    }
}
