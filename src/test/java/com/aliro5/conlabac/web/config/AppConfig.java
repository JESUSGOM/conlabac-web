package com.aliro5.conlabac.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    /**
     * Define el cliente para realizar peticiones REST al Backend (puerto 8080).
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}