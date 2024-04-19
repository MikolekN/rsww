package com.rsww.mikolekn.rsww_user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class RswwUserApplication {

	public static final String MESSAGE_TOPIC = "user_service_topic";

	static final String MESSAGE_RECEIVE_QUEUE = "user_service_receive_queue";

	static final String MESSAGE_SEND_QUEUE = "user_service_send_queue";

	@Bean
	Queue receiveQueue() {
		return new Queue(MESSAGE_RECEIVE_QUEUE, false);
	}

	@Bean
	Queue sendQueue() {
		return new Queue(MESSAGE_SEND_QUEUE, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(MESSAGE_TOPIC);
	}

	@Bean
	Binding receiveBinding(Queue receiveQueue, TopicExchange exchange) {
		return BindingBuilder.bind(receiveQueue).to(exchange).with(MESSAGE_RECEIVE_QUEUE);
	}

	@Bean
	Binding sendBinding(Queue sendQueue, TopicExchange exchange) {
		return BindingBuilder.bind(sendQueue).to(exchange).with(MESSAGE_SEND_QUEUE);
	}

	@Bean
	SimpleMessageListenerContainer receiveContainer(ConnectionFactory connectionFactory,
													MessageListenerAdapter receiveListenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(MESSAGE_RECEIVE_QUEUE);
		container.setMessageListener(receiveListenerAdapter);
		return container;
	}

	@Bean
	SimpleMessageListenerContainer sendContainer(ConnectionFactory connectionFactory,
												 MessageListenerAdapter sendListenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(MESSAGE_SEND_QUEUE);
		container.setMessageListener(sendListenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter receiveListenerAdapter(UserReceiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	public static void main(String[] args) {
		SpringApplication.run(RswwUserApplication.class, args);
	}

}
