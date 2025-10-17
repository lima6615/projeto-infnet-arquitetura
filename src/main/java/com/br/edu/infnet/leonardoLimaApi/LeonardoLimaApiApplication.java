package com.br.edu.infnet.leonardoLimaApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class LeonardoLimaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeonardoLimaApiApplication.class, args);
    }
}
