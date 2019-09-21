package com.progressivecoder.ordermanagement.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;




@ComponentScan(basePackages = {"com.progressivecoder.ordermanagement.orderservice.*"})
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "com.progressivecoder.ordermanagement.orderservice.repository")
@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
