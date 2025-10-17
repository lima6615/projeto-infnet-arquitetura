package com.br.edu.infnet.leonardoLimaApi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${spring.backend.host}")
    private String apiCep;

    @Bean
    public WebClient getWebClient() {
        return WebClient.builder().baseUrl(apiCep).build();
    }
}
