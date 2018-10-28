package com.ankurpathak.springsessionreactivetest;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RestServerAccessDeniedHandler implements ServerAccessDeniedHandler {

    private final IFilterService filterService;

    public RestServerAccessDeniedHandler(IFilterService filterService) {
        this.filterService = filterService;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException ex) {
        return filterService.generateForbidden(exchange);
    }
}
