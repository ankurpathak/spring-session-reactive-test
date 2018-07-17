package com.ankurpathak.springsessionreactivetest;


import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import static com.ankurpathak.springsessionreactivetest.RequestMappingPaths.*;
import static com.ankurpathak.springsessionreactivetest.Views.VIEW_INDEX;

@Configuration
public class ReactiveWebConfig  implements WebFluxConfigurer {



    @Controller
    public static class ViewControllers {

        @GetMapping({PATH_ROOT, PATH_INDEX})
        public String index(){
            return VIEW_INDEX;
        }

    }
}
