package com.ankurpathak.springsessionreactivetest;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Callable;

public class AsyncUtil {
    public static <T> Mono<T> async(Callable<T> callable){
        return Mono.fromCallable(callable).publishOn(Schedulers.elastic());
    }

    public static <T> Mono<T> async(Mono<T> mono){
        return mono.publishOn(Schedulers.elastic());
    }

    public static Mono<Object> async(Runnable runnable){
        return Mono.fromRunnable(runnable).publishOn(Schedulers.elastic());
    }
}
