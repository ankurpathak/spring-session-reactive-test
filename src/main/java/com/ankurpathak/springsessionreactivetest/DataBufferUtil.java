package com.ankurpathak.springsessionreactivetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Collections;

import static java.util.Collections.emptyMap;
import static org.springframework.core.ResolvableType.forClass;

public class DataBufferUtil {

    public static <T> Flux<DataBuffer> toDataBuffer(T t, Class<T> type, Jackson2JsonEncoder encoder){
        ResolvableType elementType = ResolvableType.forClass(type);
        DataBufferFactory bufferFactory = new DefaultDataBufferFactory();
        return encoder.encode(Mono.just(t), bufferFactory, elementType, MediaType.APPLICATION_JSON, emptyMap());
    }


    public static <T> Mono<T> fromDataBuffer(Flux<DataBuffer> body, Class<T> type, Jackson2JsonDecoder decoder){
        ResolvableType elementType = forClass(type);
        return decoder.decodeToMono(body, elementType, MediaType.APPLICATION_JSON, Collections.emptyMap()).cast(type);
    }
}
