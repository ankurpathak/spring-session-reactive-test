package com.ankurpathak.springsessionreactivetest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

import static org.hamcrest.Matchers.*;
import static org.valid4j.Assertive.require;

public abstract class AbstractReactiveDomainService<T extends Domain<ID>, ID extends Serializable> implements IReactiveDomainService<T, ID> {

    private final ExtendedReactiveMongoRepository<T,ID> dao;

    protected AbstractReactiveDomainService(ExtendedReactiveMongoRepository<T, ID> dao) {
        this.dao = dao;
    }

    @Override
    public Mono<T> findById(final ID id) {
        require(id, notNullValue());
        return dao.findById(id);
    }


    @Override
    public Flux<T> findAll() {
        return dao.findAll();
    }

    @Override
    public Mono<Long> countByCriteria(Criteria criteria, Class<T> type) {
        require(criteria, notNullValue());
        require(type, notNullValue());
        return dao.countByCriteria(criteria, type);
    }





    @Override
    public Flux<T> findByCriteria(final Criteria criteria, final Pageable pageable, Class<T> type) {
        require(criteria, notNullValue());
        require(pageable, notNullValue());
        require(type, notNullValue());
        return dao.findByCriteria(criteria,pageable, type);
    }



    @Override
    public Flux<T> findAll(final Pageable pageable) {
        require(pageable, notNullValue());
        return dao.findAll(pageable);
    }


    @Override
    public Mono<Page<T>> all(final Pageable pageable){
        return findAll(pageable)
                .collectList()
                .zipWith(count())
                .map(t -> new PageImpl<>(t.getT1(), pageable, t.getT2()));
    }


    @Override
    public Mono<Page<T>> byCriteria(final Criteria criteria, Pageable pageable, Class<T> type){
        return findByCriteria(criteria, pageable, type)
                .collectList()
                .zipWith(countByCriteria(criteria, type))
                .map(t -> new PageImpl<>(t.getT1(), pageable, t.getT2()));
    }






    @Override
    public Mono<T> create(T entity) {
        require(entity, notNullValue());
        return dao.insert(entity);
    }

    @Override
    public Mono<T> update(T entity) {
        require(entity, notNullValue());
        return dao.save(entity);
    }

    @Override
    public Flux<T> createAll(Iterable<T> entities) {
        require(entities, not(empty()));
        return dao.insert(entities);
    }

    @Override
    public Mono<Void> delete(final T entity) {
        require(entity, notNullValue());
        return dao.delete(entity);
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        require(id, notNullValue());
        return dao.deleteById(id);
    }


    @Override
    public String domainName(){
        String name = this.getClass().getSimpleName();
        name = name != null ? name : "";
        int index = name.indexOf('S');
        index = index > -1 ? index : 1;
        return name.substring(1, index).trim();
    }


    /* @Override
    public Page<T> findByField(String field, String value, Pageable pageable, Class<T> type) {
        require(field, not(isEmptyString()));
        require(value, not(isEmptyString()));
        require(pageable, notNullValue());
        require(type, notNullValue());
        return getDao().findByField(field, value, pageable, type);
    }

    @Override
    public Page<String> listField(String field, String value, Pageable pageable, Class<T> type) {
        require(field, not(isEmptyString()));
        require(value, not(isEmptyString()));
        require(pageable, notNullValue());
        require(type, notNullValue());
        return getDao().listField(field, value, pageable, type);
    } */

    @Override
    public Mono<Void> deleteAll() {
        return dao.deleteAll();
    }

    @Override
    public Mono<Void> deleteAll(Iterable<T> domains) {
        require(domains, not(empty()));
        return dao.deleteAll(domains);
    }


    @Override
    public Mono<Long> count() {
        return dao.count();
    }



}
