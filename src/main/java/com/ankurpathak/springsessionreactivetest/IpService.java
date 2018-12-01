package com.ankurpathak.springsessionreactivetest;

import reactor.core.publisher.Mono;

import java.util.Optional;

public interface IpService {
    Mono<String> ipToCountryAlphaCode(String ip);
}
