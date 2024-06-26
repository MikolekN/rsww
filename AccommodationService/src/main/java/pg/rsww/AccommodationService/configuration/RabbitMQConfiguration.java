package pg.rsww.AccommodationService.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RabbitMQConfiguration {
    static final String accommodationTopic = "accommodation-topic";

    @Bean
    public Queue hotelCreateQueue(@Value("${spring.rabbitmq.queue.hotelCreateQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue hotelCreatedQueue(@Value("${spring.rabbitmq.queue.hotelCreatedQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue roomCreateQueue(@Value("${spring.rabbitmq.queue.roomCreateQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue roomCreatedQueue(@Value("${spring.rabbitmq.queue.roomCreatedQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue reservationMakeQueue(@Value("${spring.rabbitmq.queue.reservationMakeQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue reservationCancelQueue(@Value("${spring.rabbitmq.queue.reservationCancelQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue reservationMadeQueue(@Value("${spring.rabbitmq.queue.reservationMadeQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue reservationCancelledQueue(@Value("${spring.rabbitmq.queue.reservationCancelledQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getAllHotelsRequestQueue(@Value("${spring.rabbitmq.queue.hotelAllRequestQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getAllHotelsResponseQueue(@Value("${spring.rabbitmq.queue.hotelAllResponseQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getHotelInfoRequestQueue(@Value("${spring.rabbitmq.queue.hotelInfoRequestQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getHotelInfoResponseQueue(@Value("${spring.rabbitmq.queue.hotelInfoResponseQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getCountryAccommodationQueue(@Value("${spring.rabbitmq.queue.countryAccommodationQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getAllHotelsQueue(@Value("${spring.rabbitmq.queue.GetAllHotelsQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getAllRoomTypesQueue(@Value("${spring.rabbitmq.queue.GetAllRoomTypesQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue removeHotelQueue(@Value("${spring.rabbitmq.queue.RemoveHotelQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue changeRoomPriceQueue(@Value("${spring.rabbitmq.queue.ChangeRoomPriceQueue}") String queue) {
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
    public Queue RoomPriceChangedEventQueue(@Value("${spring.rabbitmq.queue.RoomPriceChangedEventQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue HotelRemovedEventQueue(@Value("${spring.rabbitmq.queue.HotelRemovedEventQueue}") String queue) {
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
    TopicExchange exchange() {
        return new TopicExchange(accommodationTopic);
    }

    @Bean
    TopicExchange exchange2() {
        return new TopicExchange("accommodation-topic-2");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
