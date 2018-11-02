package com.ankurpathak.springsessionreactivetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;

@Configuration
public class AppConfig {


    private final ObjectMapper objectMapper;


    public AppConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    

    @Bean
    public Jackson2JsonEncoder jsonEncoder(){
        return new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON);
    }


    @Bean
    public Jackson2JsonDecoder jsonDecoder(){
        return new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON);
    }
}
