package com.progressivecoder.ordermanagement.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@EnableAutoConfiguration
@SpringBootApplication
public class SagaOrchestratorApplication {

	public static void main(String[] args) {
		System.out.println("App started");
		SpringApplication.run(SagaOrchestratorApplication.class, args);
	}
	
	

}
