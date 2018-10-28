package com.ankurpathak.springsessionreactivetest;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.List;

import static com.ankurpathak.springsessionreactivetest.RequestMappingPaths.*;

@ApiController
public class UserController extends AbstractRestController<User, BigInteger, UserDto> {

    private final IReactiveUserService service;

    public UserController(IReactiveUserService service, IMessageService messageService) {
        super(messageService);
        this.service = service;
    }

    @Override
    public IReactiveDomainService<User, BigInteger> getService() {
        return service;
    }



    @GetMapping(PATH_GET_USERS)
    @JsonView(View.Public.class)
    public ResponseEntity<Mono<?>> all(@RequestParam(name = "size", required = false, defaultValue = "20") String size, @RequestParam(value = "page", required = false, defaultValue = "0") String page, @RequestParam(value = "sort", required = false) String sort){
        Pageable pageable = ControllerUtil.getPageable(PrimitiveUtils.toInteger(page), PrimitiveUtils.toInteger(size), sort);
        return paginated(pageable, User.class);
    }




}
