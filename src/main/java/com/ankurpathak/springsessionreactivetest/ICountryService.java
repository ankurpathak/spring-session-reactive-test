package com.ankurpathak.springsessionreactivetest;

import reactor.core.publisher.Flux;

import java.util.List;

public interface ICountryService {
    Flux<String> alphaCodeToCallingCodes(String alpha2Code);
}
