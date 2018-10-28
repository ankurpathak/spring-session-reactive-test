package com.ankurpathak.springsessionreactivetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
public class SpringSessionReactiveTestApplication {
    public static void main(String[] args) {

        SpringApplication.run(SpringSessionReactiveTestApplication.class, args);

    }
}
