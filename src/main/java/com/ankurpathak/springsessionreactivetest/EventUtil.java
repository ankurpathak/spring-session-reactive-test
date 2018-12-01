package com.ankurpathak.springsessionreactivetest;

import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

public class EventUtil {

    private EventUtil(){}

    public static Mono<?> fireRegistrationCompleteEvent(ApplicationContext applicationContext, User user) {
        return Mono.just(applicationContext.getBean(RegistrationCompleteListener.class))
                .transform(AsyncUtil::async)
                .doOnSuccess(l -> l.onApplicationEvent(new RegistrationCompleteEvent(user)));
    }


    public static Mono<?> firePaginatedResultsRetrievedEvent(Page<?> page, UriComponentsBuilder uriBuilder, ServerWebExchange exchange) {
        return Mono.just(new PaginatedResultsRetrievedEventDiscoverabilityListener())
                .doOnSuccess(l -> l.onApplicationEvent(new PaginatedResultsRetrievedEvent(page, uriBuilder, exchange)));
    }


    public static <T extends Domain<?>> Mono<?> fireDomainCreatedEvent(T t, UriComponentsBuilder uriBuilder, ServerWebExchange exchange) {
        return Mono.just(new DomainCreatedDiscoverabilityListener())
                .doOnSuccess(l -> l.onApplicationEvent(new DomainCreatedEvent(t, uriBuilder, exchange)));
    }
}
