package com.ankurpathak.springsessionreactivetest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.ankurpathak.springsessionreactivetest.RequestMappingPaths.PATH_API;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(PATH_API)
@RestController
public @interface ApiController {
}




