package com.ankurpathak.springsessionreactivetest;

import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public class RestServerLoginAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {


    private final Jackson2JsonDecoder decoder;

    public RestServerLoginAuthenticationConverter(Jackson2JsonDecoder decoder) {
        this.decoder = decoder;
    }


    public Mono<Authentication> apply(ServerWebExchange exchange) {
        return DataBufferUtil.fromDataBuffer(exchange.getRequest().getBody(), LoginRequestDto.class, decoder)
                .map(this::createAuthentication);
    }

    private UsernamePasswordAuthenticationToken createAuthentication(LoginRequestDto data) {
        String username = data.getUsername();
        String password = data.getPassword();
        return new UsernamePasswordAuthenticationToken(username, password);
    }

}
