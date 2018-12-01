package com.ankurpathak.springsessionreactivetest;

import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import java.util.function.Function;
public class ReactiveDomainContextHolder {

    private static final Class<?> SECURITY_CONTEXT_KEY = DomainContext.class;


    public static Mono<DomainContext> getContext() {
        return Mono.subscriberContext()
                .filter( c -> c.hasKey(SECURITY_CONTEXT_KEY))
                .flatMap( c-> c.<Mono<DomainContext>>get(SECURITY_CONTEXT_KEY));
    }

    
    public static Function<Context, Context> clearContext() {
        return context -> context.delete(SECURITY_CONTEXT_KEY);
    }

    
    public static Context withDomainContext(Mono<? extends DomainContext> domainContext) {
        return Context.of(SECURITY_CONTEXT_KEY, domainContext);
    }

   
}
