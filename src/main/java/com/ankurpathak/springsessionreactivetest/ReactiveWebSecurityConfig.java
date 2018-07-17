package com.ankurpathak.springsessionreactivetest;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class ReactiveWebSecurityConfig {




    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, RequestMappingPaths.PATH_ROOT).hasAuthority(Role.ROLE_ADMIN)
                .anyExchange().denyAll()
                .and()
                .formLogin()
                .and().csrf().disable();
        return http.build();
    }

}
