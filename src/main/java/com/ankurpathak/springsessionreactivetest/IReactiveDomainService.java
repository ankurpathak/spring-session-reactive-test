package com.ankurpathak.springsessionreactivetest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface IReactiveDomainService<T extends Domain<ID>, ID extends Serializable> {

    // read - one

    Mono<T> findById(ID id);

    // read - all

    Flux<T> findAll();



    Flux<T> findPaginated(Pageable pageable);



    Flux<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> type);


    Mono<Long> countByCriteria(Criteria criteria, Class<T> type);

    // write



    Mono<T> create(T entity);

    Mono<T> update(T entity);



    Flux<T> createAll(Iterable<T> entities);


    Mono<Long> count();



    Mono<Void> delete(T entity);

    Mono<Void> deleteById(ID id);

    String domainName();

    /*

    Page<T> findByField(String field, String value, Pageable pageable, Class<T> type);
    Page<String> listField(String field, String value, Pageable pageable, Class<T> type);

    */


    Mono<Void> deleteAll(Iterable<T> domains);

    Mono<Void> deleteAll();



}
