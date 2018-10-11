package com.ankurpathak.springsessionreactivetest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

import static org.hamcrest.Matchers.*;
import static org.valid4j.Assertive.ensure;

public abstract class AbstractReactiveDomainService<T extends Domain<ID>, ID extends Serializable> implements IReactiveDomainService<T, ID> {
    @Override
    public Mono<T> findById(final ID id) {
        ensure(id, notNullValue());
        return getDao().findById(id);
    }


    @Override
    public Flux<T> findAll() {
        return getDao().findAll();
    }

    @Override
    public Mono<Long> countByCriteria(Criteria criteria, Class<T> type) {
        ensure(criteria, notNullValue());
        ensure(type, notNullValue());
        return getDao().countByCriteria(criteria, type);
    }

    protected abstract ExtendedReactiveMongoRepository<T, ID> getDao();




    @Override
    public Flux<T> findByCriteria(final Criteria criteria, final Pageable pageable, Class<T> type) {
        ensure(criteria, notNullValue());
        ensure(pageable, notNullValue());
        ensure(type, notNullValue());
        return getDao().findByCriteria(criteria,pageable, type);
    }



    @Override
    public Flux<T> findPaginated(final Pageable pageable) {
        ensure(pageable, notNullValue());
        return getDao().findAll(pageable);
    }



    @Override
    public Mono<T> create(T entity) {
        ensure(entity, notNullValue());
        return getDao().insert(entity);
    }

    @Override
    public Mono<T> update(T entity) {
        ensure(entity, notNullValue());
        return getDao().save(entity);
    }

    @Override
    public Flux<T> createAll(Iterable<T> entities) {
        ensure(entities, not(empty()));
        return getDao().insert(entities);
    }

    @Override
    public Mono<Void> delete(final T entity) {
        ensure(entity, notNullValue());
        return getDao().delete(entity);
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        ensure(id, notNullValue());
        return getDao().deleteById(id);
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
        ensure(field, not(isEmptyString()));
        ensure(value, not(isEmptyString()));
        ensure(pageable, notNullValue());
        ensure(type, notNullValue());
        return getDao().findByField(field, value, pageable, type);
    }

    @Override
    public Page<String> listField(String field, String value, Pageable pageable, Class<T> type) {
        ensure(field, not(isEmptyString()));
        ensure(value, not(isEmptyString()));
        ensure(pageable, notNullValue());
        ensure(type, notNullValue());
        return getDao().listField(field, value, pageable, type);
    } */

    @Override
    public Mono<Void> deleteAll() {
        return getDao().deleteAll();
    }

    @Override
    public Mono<Void> deleteAll(Iterable<T> domains) {
        ensure(domains, not(empty()));
        return getDao().deleteAll(domains);
    }


    @Override
    public Mono<Long> count() {
        return getDao().count();
    }
}
