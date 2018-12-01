package com.ankurpathak.springsessionreactivetest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Optional;

public abstract class AbstractRestController<T extends Domain<ID>, ID extends Serializable, TDto extends DomainDto<T, ID>> {
    private static final Logger log = LoggerFactory.getLogger(AbstractRestController.class);

    protected final IControllerService controllerService;
    protected final ApplicationContext applicationContext;

    protected AbstractRestController(ApplicationContext applicationContext, IControllerService controllerService) {
        this.applicationContext = applicationContext;
        this.controllerService = controllerService;
    }


    abstract public IReactiveDomainService<T, ID> getService();


    protected ResponseEntity<Mono<T>> byId(ID id, Class<T> type) {
        return ResponseEntity.ok(
                getService().findById(id)
                        .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id), Params.ID, type.getSimpleName(), ApiCode.NOT_FOUND)))
        );
    }


    protected ResponseEntity<Flux<T>> all() {
        return ResponseEntity.ok().body(getService().findAll());
    }


    protected ResponseEntity<Mono<?>> paginated(final Pageable pageable, Class<T> type, UriComponentsBuilder uriBuilder, ServerWebExchange exchange) {
        return ResponseEntity.ok().body(
                Mono.just(pageable)
                        .transform(PagingUtil::pagePreCheck)
                        .flatMap(getService()::all)
                        .transform(PagingUtil::pagePostCheck)
                        .delayUntil(page -> EventUtil.firePaginatedResultsRetrievedEvent(page, uriBuilder, exchange))
                        .map(Page::getContent)
        );


    }



    protected Mono<T> tryCreateOne(TDto vDto, ServerWebExchange exchange, UriComponentsBuilder uriBuilder, IToDomain<T, ID, TDto> converter) {
        return  Mono.just(vDto)
                .map(converter::toDomain)
                .flatMap(getService()::create)
                .delayUntil(vT -> EventUtil.fireDomainCreatedEvent(vT, uriBuilder, exchange));

    }



    protected Mono<T> createOne(Mono<TDto> dto, ServerWebExchange exchange, UriComponentsBuilder uriBuilder, IToDomain<T, ID, TDto> converter) {
        return controllerService.processValidation(dto)
                .flatMap(vDto ->
                        tryCreateOne(vDto, exchange, uriBuilder, converter)
                        .doOnError(ex -> log.error("{} message: {} cause: {}",ex.getClass().getSimpleName(),  ex.getMessage(), ex.getCause()))
                        .onErrorMap(ex -> catchCreateOne(ex, vDto, exchange))
                );
    }


    protected Throwable catchCreateOne(Throwable e, TDto dto, ServerWebExchange exchange) {
       return Optional.of(e)
                .filter(DuplicateKeyException.class::isInstance)
                .map(DuplicateKeyException.class::cast)
                .map(x -> ApplicationExceptionProcessor.processDuplicateKeyException(x,dto,controllerService))
                .map(Throwable.class::cast)
                .orElse(e);

    }


}
