package com.ankurpathak.springsessionreactivetest;

import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.Map;

import static com.ankurpathak.springsessionreactivetest.Params.EMAIL;
import static com.ankurpathak.springsessionreactivetest.RequestMappingPaths.PATH_ACCOUNT;
import static com.ankurpathak.springsessionreactivetest.RequestMappingPaths.PATH_ACCOUNT_EMAIL;

@ApiController
public class AccountController extends AbstractRestController<User, BigInteger, UserDto> {

    private final IReactiveUserService service;
    private final DomainConverters domainConverters;

    public AccountController(ApplicationContext applicationContext, IControllerService controllerService, IReactiveUserService service, DomainConverters domainConverters) {
        super(applicationContext, controllerService);
        this.service = service;
        this.domainConverters = domainConverters;
    }

    @Override
    public IReactiveDomainService<User, BigInteger> getService() {
        return service;
    }


    @PostMapping(PATH_ACCOUNT)
    public ResponseEntity<Mono<?>> account(ServerWebExchange exchange, UriComponentsBuilder uriBuilder, @Validated({UserDto.Default.class, UserDto.Register.class}) @RequestBody Mono<UserDto> dto) {
        return ResponseEntity.ok().body(
                    createOne(dto, exchange, uriBuilder, domainConverters.userDtoRegisterToDomain())
                            .delayUntil(user -> EventUtil.fireRegistrationCompleteEvent(applicationContext, user))
                            .map(user -> controllerService.processSuccess(Map.of(Params.ID, user.getId())))
                );
    }



    @PutMapping(PATH_ACCOUNT_EMAIL)
    public ResponseEntity<Mono<?>> accountEnableEmail(ServerWebExchange exchange, UriComponentsBuilder uriBuilder, @PathVariable(EMAIL) String email){
        return null;
    }




}
