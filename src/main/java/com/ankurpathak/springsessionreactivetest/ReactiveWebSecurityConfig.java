package com.ankurpathak.springsessionreactivetest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


import static com.ankurpathak.springsessionreactivetest.RequestMappingPaths.*;
@Configuration
public class ReactiveWebSecurityConfig {

    @Autowired
    private FilterConfig filterConfig;

    @Autowired
    private RestServerAuthenticationEntryPoint authenticationEntryPoint;


    @Autowired
    private RestServerAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/").permitAll()
                .pathMatchers(HttpMethod.POST, "/").permitAll()
                .pathMatchers(HttpMethod.POST, apiPath(PATH_ACCOUNT)).permitAll()
                .pathMatchers(HttpMethod.GET, RequestMappingPaths.PATH_ROOT).hasAuthority(Role.ROLE_ADMIN)
                .pathMatchers(HttpMethod.GET, RequestMappingPaths.PATH_FAVICON).permitAll()
                .pathMatchers(HttpMethod.GET, apiPath(PATH_GET_ME)).hasAuthority(Role.ROLE_ADMIN)
                .pathMatchers(HttpMethod.GET, apiPath(PATH_GET_USERS)).hasAuthority(Role.ROLE_ADMIN)
                .anyExchange().denyAll()
                .and()
                .csrf().disable()
                .addFilterAt(filterConfig.domainContextWebFilter(), SecurityWebFiltersOrder.REACTOR_CONTEXT)
                .addFilterAt(filterConfig.authenticationWebFilter(), SecurityWebFiltersOrder.FORM_LOGIN)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
        ;
        return http.build();

    }


}
