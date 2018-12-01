package com.ankurpathak.springsessionreactivetest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

import static com.ankurpathak.springsessionreactivetest.RequestMappingPaths.PATH_GET_USERS;

@ApiController
public class UserController extends AbstractRestController<User, BigInteger, UserDto> {

    private final IReactiveUserService service;

    public UserController(ApplicationContext applicationContext, IControllerService controllerService, IReactiveUserService service) {
        super(applicationContext, controllerService);
        this.service = service;
    }

    @Override
    public IReactiveDomainService<User, BigInteger> getService() {
        return service;
    }



    @GetMapping(PATH_GET_USERS)
    @JsonView(View.Public.class)
    public ResponseEntity<Mono<?>> all(@RequestParam(name = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "page", required = false, defaultValue = "0") String page, @RequestParam(value = "sort", required = false) String sort, UriComponentsBuilder uriBuilder, ServerWebExchange exchange){
        Pageable pageable = PagingUtil.getPageable(PagingUtil.fixPage(page), PagingUtil.fixSize(size), sort);
        return paginated(pageable, User.class, uriBuilder, exchange);
    }







}
