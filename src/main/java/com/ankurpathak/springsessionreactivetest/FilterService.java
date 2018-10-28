package com.ankurpathak.springsessionreactivetest;

import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Service
public class FilterService implements IFilterService {
    private final Jackson2JsonEncoder jsonEncoder;
    private final IMessageService messageService;

    public FilterService(Jackson2JsonEncoder jsonEncoder, IMessageService messageService) {
        this.jsonEncoder = jsonEncoder;
        this.messageService = messageService;
    }

    @Override
    public Mono<Void> generateUnauthorized(ServerWebExchange exchange, AuthenticationException ex) {
        return FilterUtil.generateUnauthorized(exchange, jsonEncoder, messageService, ex);
    }

    @Override
    public Mono<Void> generateUnauthorized(ServerWebExchange exchange) {
        return FilterUtil.generateUnauthorized(exchange, jsonEncoder, messageService);
    }

    @Override
    public Mono<Void> generateSuccess(ServerWebExchange exchange) {
        return FilterUtil.generateSuccess(exchange, jsonEncoder, messageService);
    }

    @Override
    public Mono<Void> generateForbidden(ServerWebExchange exchange) {
        return FilterUtil.generateForbidden(exchange, jsonEncoder, messageService);
    }
}
