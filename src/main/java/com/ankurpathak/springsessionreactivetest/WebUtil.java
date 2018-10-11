package com.ankurpathak.springsessionreactivetest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class WebUtil {


    public static final void setContentTypeApplicationJson(HttpHeaders httpHeaders){
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }
}
