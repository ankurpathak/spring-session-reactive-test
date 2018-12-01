package com.ankurpathak.springsessionreactivetest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import static com.ankurpathak.springsessionreactivetest.RequestMappingPaths.PATH_GET_ME;

@ApiController
public class MeController {

    @GetMapping(PATH_GET_ME)
    @JsonView(View.Me.class)
    public Mono<User> get(@CurrentUser User user){
       return Mono.just(user);
    }

}
