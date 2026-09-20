package com.eventhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.eventhub")
public class EventhubBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventhubBackendApplication.class, args);
	}

}