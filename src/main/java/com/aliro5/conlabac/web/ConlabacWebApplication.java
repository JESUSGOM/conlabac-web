package com.aliro5.conlabac.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConlabacWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConlabacWebApplication.class, args);
    }

    /**
     * Define el Bean RestTemplate.
     * Este objeto es el "cliente" que usará la Web para hacer peticiones HTTP
     * a la API (conlabac-api), permitiendo enviar los emails y consultar datos.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}