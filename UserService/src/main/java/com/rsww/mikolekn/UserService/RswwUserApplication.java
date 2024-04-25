package com.rsww.mikolekn.user_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class RswwUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(RswwUserApplication.class, args);
	}

}
