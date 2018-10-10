package com.ankurpathak.springsessionreactivetest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

@Configuration
public class FilterConfig {


    @Autowired
    private ReactiveAuthenticationManager authenticationManager;
    @Autowired
    private RestServerLoginAuthenticationConverter authenticationConverter;
    @Autowired
    private RestServerAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private RestServerAuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    @Lazy
    public AuthenticationWebFilter authenticationWebFilter(){
        AuthenticationWebFilter filter = new AuthenticationWebFilter(authenticationManager);
        filter.setAuthenticationConverter(authenticationConverter);
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        filter.setSecurityContextRepository(new WebSessionServerSecurityContextRepository());
        return  filter;
    }

}
