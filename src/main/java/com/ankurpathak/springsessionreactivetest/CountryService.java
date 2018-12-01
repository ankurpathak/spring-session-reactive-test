package com.ankurpathak.springsessionreactivetest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.valid4j.Assertive.require;

@Service
public class CountryService implements ICountryService {

    private final WebClient countryWebClient;

    public CountryService(@Qualifier("countryWebClient") WebClient countryWebClient) {
        this.countryWebClient = countryWebClient;
    }

    @SuppressWarnings("all")
    public Flux<String> alphaCodeToCallingCodes(String alphaCode) {
        //throws  javax.ws.rs.ProcessingException
        require(alphaCode, not(emptyString()));
        return countryWebClient.get()
                .uri(u -> u.pathSegment("alpha").queryParam("codes", alphaCode).build())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(json -> MonoUtil.jsonPath(json, String[].class, "$[0].callingCodes"))
                .map(Arrays::asList)
                .flatMapMany(Flux::fromIterable);
    }



}
