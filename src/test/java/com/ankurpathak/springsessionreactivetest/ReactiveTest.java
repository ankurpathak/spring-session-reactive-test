package com.ankurpathak.springsessionreactivetest;

import org.junit.Test;
import reactor.core.publisher.Mono;

public class ReactiveTest {

    @Test
    public void test(){
       // Mono.just("Hello")
        Mono.error(new RuntimeException())
                .doOnNext(System.out::println)
                .doOnError(System.out::println)
                .then(Mono.just("World")).subscribe(
                        System.out::println
        );
    }
}
