package com.ankurpathak.springsessionreactivetest;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.WebFilter;

@SpringBootApplication
public class SpringSessionReactiveTestApplication {



    public static void main(String[] args) {
       SpringApplication.run(SpringSessionReactiveTestApplication.class, args);

    }

}

@Component
class Main implements ApplicationRunner{
    final WebFilter[] filters;

    Main(WebFilter[] filters) {
        this.filters = filters;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(filters.length);
    }
}

