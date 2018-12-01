package com.ankurpathak.springsessionreactivetest;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
class DomainCreatedDiscoverabilityListener implements ApplicationListener<DomainCreatedEvent> {

    void addLinkHeaderOnResourceCreation(UriComponentsBuilder uriBuilder, final ServerWebExchange exchange, Domain<?> source) {
        final URI uri = uriBuilder.path(ControllerUtil.contextPath(exchange)).path(RequestMappingPaths.PATH_API).path(source.resourcePath()).path(String.format("{%s}", Params.ID)).buildAndExpand(String.valueOf(source.getId())).toUri();
        ControllerUtil.headers(exchange).setLocation(uri);
    }

    @Override
    public void onApplicationEvent(DomainCreatedEvent ev) {
        addLinkHeaderOnResourceCreation(ev.getUriBuilder(), ev.getExchange(), ev.getSource());
    }
}