package com.ankurpathak.springsessionreactivetest;

import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.Collections;

import static java.util.Collections.emptyMap;
import static org.springframework.core.ResolvableType.forClass;

public class FluxUtil {

    private FluxUtil(){}


    public static <T> Flux<DataBuffer> toDataBuffer(DataBufferFactory bufferFactory, Collection<T> collection, Class<T> type, Jackson2JsonEncoder encoder){
        ResolvableType elementType = ResolvableType.forClass(type);
        return encoder.encode(Flux.fromIterable(collection), bufferFactory, elementType, MediaType.APPLICATION_JSON, emptyMap());
    }


    public static <T> Flux<T> fromDataBuffer(Flux<DataBuffer> body, Class<T> type, Jackson2JsonDecoder decoder){
        ResolvableType elementType = forClass(type);
        return decoder.decode(body, elementType, MediaType.APPLICATION_JSON, Collections.emptyMap()).cast(type);
    }
}
