package com.goomo.cardvault;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EntityScan(basePackages = { "com.goomo.cardvault.model" })
@EnableJpaRepositories(basePackages = { "com.goomo.cardvault.repository" })
@SpringBootApplication
@EnableSwagger2
public class CardVaultServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(CardVaultServiceApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(CardVaultServiceApplication.class, args);
		log.info("Card Vault Service Application started successfully");
	}
	
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("Card Vault Service").apiInfo(apiInfo()).select()
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Goomo Card Vault Micro Service").description(
				"Goomo Card Vault REST API's micro service Developed in spring boot. This micro service handles storage of debit and credit card "+
		"details into separate fully PCI complined secured layer. These card details used by respective customers to choose the right card and go "
		+ "quick checkout flow.")
				.contact(new Contact("Manjunath Jakkandi","www.goomo.com","manjunath.jakkandi@goomo.com")).license("Apache License Version 2.0").version("1.1.0").build();
	}
}
