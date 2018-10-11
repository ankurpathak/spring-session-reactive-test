package com.ankurpathak.springsessionreactivetest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

public abstract class AbstractCustomizedReactiveDomainRepository<T extends Domain<ID>, ID extends Serializable> implements ICustomizedReactiveDomainRepository<T, ID> {

    abstract public ReactiveMongoTemplate getTemplate();


    @Override
    public Flux<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> type) {
        return getTemplate().find(
                new Query().with(pageable).addCriteria(criteria),
                type
        );
    }


    @Override
    public Mono<Long> countByCriteria(Criteria criteria, Class<T> type) {
        return getTemplate().count(
            new Query().addCriteria(criteria),
            type
        );
    }
}
