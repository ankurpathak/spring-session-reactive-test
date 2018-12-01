package com.ankurpathak.springsessionreactivetest;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;

final public class DomainContext implements Serializable {

    private final Mono<String> ip;

    public DomainContext(final ServerWebExchange exchange) {
        ip = Mono.just(exchange)
                .map(ServerWebExchange::getRequest)
                .map(ServerHttpRequest::getRemoteAddress)
                .map(InetSocketAddress::getAddress)
                .map(InetAddress::getHostAddress);
    }


    public Mono<String> getIp(){
        return ip;
    }


}
