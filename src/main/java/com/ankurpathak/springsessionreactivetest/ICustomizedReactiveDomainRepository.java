package com.ankurpathak.springsessionreactivetest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

public interface ICustomizedReactiveDomainRepository<T extends Domain<ID>, ID extends Serializable> {
    Flux<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> type);
    Mono<Long> countByCriteria(Criteria criteria, Class<T> type);
}
