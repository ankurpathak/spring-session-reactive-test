package com.ankurpathak.springsessionreactivetest;

import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface IFilterService {
    Mono<Void> generateUnauthorized(ServerWebExchange exchange, AuthenticationException ex);

    Mono<Void> generateUnauthorized(ServerWebExchange exchange);

    Mono<Void> generateSuccess(ServerWebExchange exchange);

    Mono<Void> generateForbidden(ServerWebExchange exchange);

}
