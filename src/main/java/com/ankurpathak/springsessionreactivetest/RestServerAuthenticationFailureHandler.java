package com.ankurpathak.springsessionreactivetest;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RestServerAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    private final IFilterService filterService;

    public RestServerAuthenticationFailureHandler(IFilterService filterService) {
        this.filterService = filterService;
    }

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException ex) {
       return filterService.generateUnauthorized(webFilterExchange.getExchange(), ex);
    }
}
