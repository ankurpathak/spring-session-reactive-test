package com.ankurpathak.springsessionreactivetest;

import org.springframework.data.domain.Page;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

public final class PaginatedResultsRetrievedEvent extends ExtendedApplicationEvent<Page<?>> {
    private final UriComponentsBuilder uriBuilder;
    private final ServerWebExchange exchange;
    public PaginatedResultsRetrievedEvent(Page<?> page, UriComponentsBuilder uriBuilder, ServerWebExchange exchange) {
        super(page);
        this.uriBuilder = uriBuilder;
        this.exchange = exchange;
    }

    public UriComponentsBuilder getUriBuilder() {
        return uriBuilder.path(ControllerUtil.contextPath(exchange));
    }

    public ServerWebExchange getExchange() {
        return exchange;
    }
}
