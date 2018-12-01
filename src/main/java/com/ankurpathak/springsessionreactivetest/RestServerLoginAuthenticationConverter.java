package com.ankurpathak.springsessionreactivetest;

import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public class RestServerLoginAuthenticationConverter implements ServerAuthenticationConverter {


    private final Jackson2JsonDecoder decoder;

    public RestServerLoginAuthenticationConverter(Jackson2JsonDecoder decoder) {
        this.decoder = decoder;
    }


    private UsernamePasswordAuthenticationToken createAuthentication(LoginRequestDto data) {
        String username = data.getUsername();
        String password = data.getPassword();
        return new UsernamePasswordAuthenticationToken(username, password);
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return MonoUtil.fromDataBuffer(exchange.getRequest().getBody(), LoginRequestDto.class, decoder)
                .map(this::createAuthentication);
    }
}
