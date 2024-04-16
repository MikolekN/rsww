package pg.rsww.AccommodationService.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RabbitMQConfiguration {
    static final String accommodationQueueName = "accommodation-queue";
    static final String accommodationTopic = "accommodation-topic";


    @Bean
    public Queue accommodationQueue() {
        return new Queue(accommodationQueueName);
    }
    @Bean
    public Queue hotelCreateQueue() {
        return new Queue("hotel-create-queue");
    }
    @Bean
    public Queue hotelCreatedQueue() {
        return new Queue("hotel-created-queue");
    }
    @Bean
    public Queue roomCreateQueue() {
        return new Queue("room-create-queue");
    }
    @Bean
    public Queue roomCreatedQueue() {
        return new Queue("room-created-queue");
    }
    @Bean
    public Queue reservationMakeQueue() {
        return new Queue("reservation-make-queue");
    }
    @Bean
    public Queue reservationCancelQueue() {
        return new Queue("reservation-cancel-queue");
    }
    @Bean
    public Queue reservationMadeQueue() {
        return new Queue("reservation-made-queue");
    }
    @Bean
    public Queue reservationCancelledQueue() {
        return new Queue("reservation-cancelled-queue");
    }
    @Bean
    public Queue getAllHotelsRequestQueue() {
        return new Queue("hotel-all-request-queue");
    }
    @Bean
    public Queue getAllHotelsResponseQueue() {
        return new Queue("hotel-all-response-queue");
    }
    @Bean
    public Queue getHotelInfoRequestQueue() {
        return new Queue("hotel-info-request-queue");
    }
    @Bean
    public Queue getHotelInfoResponseQueue() {
        return new Queue("hotel-info-response-queue");
    }
    @Bean
    public Queue accommodationQueue2() {
        return new Queue("accommodation-queue-2");
    }

    @Bean
    public Queue accommodationQueue3() {
        return new Queue("accommodation-queue-3");
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
