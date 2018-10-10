package com.ankurpathak.springsessionreactivetest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class WebUtil {
    public static final String HEADER_CONTENT_TYPE = "Content-Type";


    public static final void setContentTypeApplicationJson(HttpHeaders httpHeaders){
        httpHeaders.add(HEADER_CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }
}
