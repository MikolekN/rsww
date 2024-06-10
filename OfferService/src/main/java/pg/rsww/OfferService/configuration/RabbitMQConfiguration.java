package pg.rsww.OfferService.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    @Bean
    public Queue getAllHotelsRequestQueue(@Value("${spring.rabbitmq.queue.hotelAllRequestQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getHotelInfoRequestQueue(@Value("${spring.rabbitmq.queue.hotelInfoRequestQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getAllOffersRequestQueue(@Value("${spring.rabbitmq.queue.offerAllRequestQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getOfferInfoRequestQueue(@Value("${spring.rabbitmq.queue.offerInfoRequestQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getFindFlightQueue(@Value("${spring.rabbitmq.queue.findFlightQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getCountryQueue(@Value("${spring.rabbitmq.queue.countryQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getCountryAccommodationQueue(@Value("${spring.rabbitmq.queue.countryAccommodationQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue flightRemovedEventQueue(@Value("${spring.rabbitmq.queue.FlightRemovedEventQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue flightPriceChangedEventQueue(@Value("${spring.rabbitmq.queue.FlightPriceChangedEventQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue HotelRemovedEventOfferQueue(@Value("${spring.rabbitmq.queue.HotelRemovedEventOfferQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue RoomPriceChangedEventOfferQueue(@Value("${spring.rabbitmq.queue.RoomPriceChangedEventOfferQueue}") String queue) {
        return new Queue(queue);
    }

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
    @Bean
    public Queue getHotelChangeEventsQueue(@Value("${spring.rabbitmq.queue.GetHotelChangeEventsQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getRoomChangeEventsQueue(@Value("${spring.rabbitmq.queue.GetRoomChangeEventsQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getFlightChangeEventsQueue(@Value("${spring.rabbitmq.queue.GetFlightChangeEventsQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getFlightRemovedEventsQueue(@Value("${spring.rabbitmq.queue.GetFlightRemovedEventsQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getFlightPriceChangeEventsQueue(@Value("${spring.rabbitmq.queue.GetFlightPriceChangeEventsQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate) {
        return new AsyncRabbitTemplate(rabbitTemplate);
    }
}
