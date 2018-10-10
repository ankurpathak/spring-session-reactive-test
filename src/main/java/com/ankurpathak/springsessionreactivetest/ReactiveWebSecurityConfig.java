package com.ankurpathak.springsessionreactivetest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;


import static com.ankurpathak.springsessionreactivetest.RequestMappingPaths.*;
@Configuration
public class ReactiveWebSecurityConfig {

    @Autowired
    private AuthenticationWebFilter authenticationWebFilter;

    @Autowired
    private RestServerAuthenticationEntryPoint authenticationEntryPoint;


    @Autowired
    private RestServerAccessDeniedHandler accessDeniedHandler;


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, RequestMappingPaths.PATH_ROOT).hasAuthority(Role.ROLE_ADMIN)
                .pathMatchers(HttpMethod.GET, RequestMappingPaths.PATH_FAVICON).permitAll()
                .pathMatchers(HttpMethod.GET, apiPath(PATH_GET_ME)).hasAuthority(Role.ROLE_ADMIN)
                .anyExchange().denyAll()
                .and()
                .csrf().disable()
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        ;
        return http.build();
    }


}
