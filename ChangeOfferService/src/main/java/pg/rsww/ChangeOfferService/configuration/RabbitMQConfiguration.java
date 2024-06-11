package pg.rsww.ChangeOfferService.configuration;

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
    public Queue getAllHotelsQueue(@Value("${spring.rabbitmq.queue.GetAllHotelsQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getAllRoomTypesQueue(@Value("${spring.rabbitmq.queue.GetAllRoomTypesQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue getAllFlightsQueue(@Value("${spring.rabbitmq.queue.GetAllFlightsQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue removeHotelQueue(@Value("${spring.rabbitmq.queue.RemoveHotelQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue removeFlightQueue(@Value("${spring.rabbitmq.queue.RemoveFlightQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue changeRoomPriceQueue(@Value("${spring.rabbitmq.queue.ChangeRoomPriceQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue changeFlightPriceQueue(@Value("${spring.rabbitmq.queue.ChangeFlightPriceQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue generateFlightPriceChangeQueue(@Value("${spring.rabbitmq.queue.GenerateFlightPriceChangeQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue generateRoomPriceChangeQueue(@Value("${spring.rabbitmq.queue.GenerateRoomPriceChangeQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue generateFlightRemoveQueue(@Value("${spring.rabbitmq.queue.GenerateFlightRemoveQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public Queue generateHotelRemoveQueue(@Value("${spring.rabbitmq.queue.GenerateHotelRemoveQueue}") String queue) {
        return new Queue(queue);
    }
    @Bean
    public MessageConverter jsonMessageConverter() { return new Jackson2JsonMessageConverter();}
    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate) {
        return new AsyncRabbitTemplate(rabbitTemplate);
    }
}
