package com.progressivecoder.ordermanagement.orderservice.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateTimeoutConfig {

    private final int TIMEOUT = (int) TimeUnit.SECONDS.toMillis(10);

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(getRequestFactory());
    }

    private ClientHttpRequestFactory getRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(TIMEOUT);
        factory.setConnectTimeout(TIMEOUT);
        factory.setConnectionRequestTimeout(TIMEOUT);
        return factory;
    }
}