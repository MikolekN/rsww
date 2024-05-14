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
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate) {
        return new AsyncRabbitTemplate(rabbitTemplate);
    }
}
