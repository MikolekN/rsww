package com.rsww.lydka.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class PaymentMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentMsApplication.class, args);
	}
}
