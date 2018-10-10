package com.ankurpathak.springsessionreactivetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public class RestServerLoginAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {


    private final ObjectMapper objectMapper;

    public RestServerLoginAuthenticationConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Mono<Authentication> apply(ServerWebExchange exchange) {
        return DataBufferUtil.fromDataBuffer(exchange.getRequest().getBody(), LoginRequestDto.class, objectMapper)
                .map(this::createAuthentication);
    }

    private UsernamePasswordAuthenticationToken createAuthentication(LoginRequestDto data) {
        String username = data.getUsername();
        String password = data.getPassword();
        return new UsernamePasswordAuthenticationToken(username, password);
    }

}
