package com.ankurpathak.springsessionreactivetest;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.ReactiveMapSessionRepository;
import org.springframework.session.ReactiveSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.server.EnableSpringWebSession;
import org.springframework.web.server.session.HeaderWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableSpringWebSession
public class SessionConfig  {



    @Bean
    public WebSessionIdResolver httpSessionStrategy() {
        HeaderWebSessionIdResolver sessionIdResolver = new HeaderWebSessionIdResolver();
        sessionIdResolver.setHeaderName("X-Auth-Token");
        return sessionIdResolver;
    }


    @Bean
    public ReactiveSessionRepository sessionRepository() {
        return new ReactiveMapSessionRepository(new ConcurrentHashMap<>());
    }






}