package com.ankurpathak.springsessionreactivetest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Flux;

import java.io.Serializable;

@NoRepositoryBean
public interface ExtendedReactiveMongoRepository<T extends Domain<ID>, ID extends Serializable> extends ReactiveMongoRepository<T, ID>, ICustomizedReactiveDomainRepository<T, ID> {

    @Query("{ }")
    Flux<T> findAll(Pageable pageable);
}
