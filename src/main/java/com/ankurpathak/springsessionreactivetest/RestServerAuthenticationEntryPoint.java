package com.ankurpathak.springsessionreactivetest;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
public class RestServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    private final IFilterService filterService;

    public RestServerAuthenticationEntryPoint(IFilterService filterService) {
        this.filterService = filterService;
    }

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return filterService.generateUnauthorized(exchange, ex);
    }
}
