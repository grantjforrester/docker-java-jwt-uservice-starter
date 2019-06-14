package com.github.grantjforrester.uservice.starter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
public class ServiceConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(APPLICATION_JSON);
    }
}
