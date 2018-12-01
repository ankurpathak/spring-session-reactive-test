package com.ankurpathak.springsessionreactivetest;

import org.jsfr.json.*;
import org.jsfr.json.provider.JacksonProvider;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.util.Collections;
import java.util.function.Consumer;

import static java.util.Collections.emptyMap;
import static org.springframework.core.ResolvableType.forClass;

public class MonoUtil {

    private MonoUtil() {
    }

    public static <T> Flux<DataBuffer> toDataBuffer(DataBufferFactory bufferFactory, T t, Class<T> type, Jackson2JsonEncoder encoder) {
        ResolvableType elementType = ResolvableType.forClass(type);
        return encoder.encode(Mono.just(t), bufferFactory, elementType, MediaType.APPLICATION_JSON, emptyMap());
    }


    public static <T> Mono<T> fromDataBuffer(Flux<DataBuffer> body, Class<T> type, Jackson2JsonDecoder decoder) {
        ResolvableType elementType = forClass(type);
        return decoder.decodeToMono(body, elementType, MediaType.APPLICATION_JSON, Collections.emptyMap()).cast(type);
    }

    @SuppressWarnings("unchecked")
    public static <T> Mono<T> jsonPath(String json, Class<T> type, String jsonPath) {
        JsonSurfer surfer = JsonSurferJackson.INSTANCE;
        return Mono.create((Consumer<MonoSink<T>>)  sink -> {
            surfer.configBuilder().bind(jsonPath, type, (value, context) -> sink.success(value)).buildAndSurf(json);
        });
    }






}
