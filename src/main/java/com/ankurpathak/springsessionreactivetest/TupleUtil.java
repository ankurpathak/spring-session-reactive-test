package com.ankurpathak.springsessionreactivetest;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import static org.hamcrest.Matchers.notNullValue;
import static org.valid4j.Assertive.require;

public class TupleUtil {

    public static <T1, T2, T3> Mono<Tuple3<T1, T2, T3>> createTuple(T1 t1, T2 t2, T3 t3){
        require(t1, notNullValue());
        require(t2, notNullValue());
        require(t3, notNullValue());
        return Mono.zip(Mono.just(t1), Mono.just(t2), Mono.just(t3));
    }

    public static <T1, T2> Mono<Tuple2<T1, T2>> createTuple(T1 t1, T2 t2){
        require(t1, notNullValue());
        require(t2, notNullValue());
        return Mono.zip(Mono.just(t1), Mono.just(t2));
    }
}
