package com.ankurpathak.springsessionreactivetest;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@Configuration
public class FilterConfig {


    private final ReactiveAuthenticationManager authenticationManager;
    private final RestServerLoginAuthenticationConverter authenticationConverter;
    private final RestServerAuthenticationFailureHandler authenticationFailureHandler;
    private final RestServerAuthenticationSuccessHandler authenticationSuccessHandler;

    public FilterConfig(ReactiveAuthenticationManager authenticationManager, RestServerLoginAuthenticationConverter authenticationConverter, RestServerAuthenticationFailureHandler authenticationFailureHandler, RestServerAuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationManager = authenticationManager;
        this.authenticationConverter = authenticationConverter;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    protected AuthenticationWebFilter authenticationWebFilter(){
        AuthenticationWebFilter filter = new AuthenticationWebFilter(authenticationManager);
        filter.setServerAuthenticationConverter(authenticationConverter);
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        filter.setSecurityContextRepository(new WebSessionServerSecurityContextRepository());
        filter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/login"));
        return  filter;
    }


    protected DomainContextWebFilter domainContextWebFilter(){
        return new DomainContextWebFilter();
    }

}
