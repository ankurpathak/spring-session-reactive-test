package com.ankurpathak.springsessionreactivetest;

import java.nio.file.Paths;

public interface RequestMappingPaths {
    String PATH_API = "/api";
    String PATH_GET_ME = "/me";


    String PATH_GET_USER = "/users/{id}";

    String PATH_GET_USERS = "/users";

    String PATH_GET_ERROR = "/error";

    String PATH_ROOT = "/";

    String PATH_INDEX = "index";
    String PATH_FAVICON = "/favicon.ico";


    static String apiPath(String path){
        return Paths.get(PATH_API, path).toString();
    }

}
