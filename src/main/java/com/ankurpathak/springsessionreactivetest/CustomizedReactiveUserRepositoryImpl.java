package com.ankurpathak.springsessionreactivetest;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public class CustomizedReactiveUserRepositoryImpl extends AbstractCustomizedReactiveDomainRepository<User, BigInteger> implements CustomizedReactiveUserRepository {
    private final ReactiveMongoTemplate template;

    public CustomizedReactiveUserRepositoryImpl(ReactiveMongoTemplate template) {
        this.template = template;
    }

    @Override
    public ReactiveMongoTemplate getTemplate() {
        return template;
    }
}