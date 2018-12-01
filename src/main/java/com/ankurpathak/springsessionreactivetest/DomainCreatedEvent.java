package com.ankurpathak.springsessionreactivetest;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

public class DomainCreatedEvent extends ExtendedApplicationEvent<Domain<?>> {
    private final ServerWebExchange exchange;
    private final UriComponentsBuilder uriBuilder;

    public DomainCreatedEvent(final Domain<?> source, UriComponentsBuilder uriBuilder, final ServerWebExchange exchange) {
        super(source);
        this.uriBuilder = uriBuilder;
        this.exchange = exchange;
    }

    public ServerWebExchange getExchange() {
        return exchange;
    }

    public UriComponentsBuilder getUriBuilder() {
        return uriBuilder;
    }

    public Object getId() {
        return getSource().getId();
    }
}
