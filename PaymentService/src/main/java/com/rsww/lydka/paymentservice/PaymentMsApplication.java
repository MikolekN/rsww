package com.rsww.lydka.paymentms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsww.lydka.paymentms.payment.response.PaymentResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class PaymentMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentMsApplication.class, args);
	}
}
