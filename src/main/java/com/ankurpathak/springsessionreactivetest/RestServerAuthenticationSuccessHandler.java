package com.ankurpathak.springsessionreactivetest;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RestServerAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    private final IFilterService filterService;

    public RestServerAuthenticationSuccessHandler(IFilterService filterService) {
        this.filterService = filterService;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
      return filterService.generateSuccess(webFilterExchange.getExchange());
    }
}
