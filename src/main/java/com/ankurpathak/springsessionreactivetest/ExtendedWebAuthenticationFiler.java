package com.ankurpathak.springsessionreactivetest;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class ExtendedWebAuthenticationFiler extends AuthenticationWebFilter {

    private ServerSecurityContextRepository securityContextRepository = NoOpServerSecurityContextRepository.getInstance();;
    private ServerAuthenticationSuccessHandler authenticationSuccessHandler = new WebFilterChainServerAuthenticationSuccessHandler();
    ;

    public ExtendedWebAuthenticationFiler(ReactiveAuthenticationManager authenticationManager) {
        super(authenticationManager);
    }



    protected Mono<Void> onAuthenticationSuccess(Authentication authentication, WebFilterExchange webFilterExchange) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        ExtendedSecurityContextImpl securityContext = new ExtendedSecurityContextImpl();
        securityContext.setAuthentication(authentication);
        return this.securityContextRepository.save(exchange, securityContext)
                .then(this.authenticationSuccessHandler
                        .onAuthenticationSuccess(webFilterExchange, authentication))
                .subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
    }


    public void setSecurityContextRepository(ServerSecurityContextRepository securityContextRepository) {
        super.setSecurityContextRepository(securityContextRepository);
        this.securityContextRepository = securityContextRepository;
    }


    @Override
    public void setAuthenticationSuccessHandler(ServerAuthenticationSuccessHandler authenticationSuccessHandler) {
        super.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        this.authenticationSuccessHandler = authenticationSuccessHandler;

    }
}
