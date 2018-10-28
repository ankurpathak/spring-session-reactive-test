package com.ankurpathak.springsessionreactivetest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

public abstract class AbstractRestController<T extends Domain<ID>, ID extends Serializable, TDto extends DomainDto<T, ID>> {
    protected final IMessageService messageService;

    protected AbstractRestController(IMessageService messageService) {
        this.messageService = messageService;
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


    protected ResponseEntity<Mono<?>> paginated(final Pageable pageable, Class<T> type) {
        return ResponseEntity.ok(
                ControllerUtil.pagePreCheck(pageable)
                        .then(getService().all(pageable))
                        .flatMap(ControllerUtil::pagePostCheck)
                        .map(Page::getContent)
        );
    }

}
