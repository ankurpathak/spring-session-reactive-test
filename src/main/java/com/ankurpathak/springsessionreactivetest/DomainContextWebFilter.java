package com.ankurpathak.springsessionreactivetest;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class DomainContextWebFilter implements WebFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        DomainContext context = new DomainContext(exchange);
        return chain.filter(exchange)
                .subscriberContext(ReactiveDomainContextHolder.withDomainContext(Mono.just(context)));
    }
}
