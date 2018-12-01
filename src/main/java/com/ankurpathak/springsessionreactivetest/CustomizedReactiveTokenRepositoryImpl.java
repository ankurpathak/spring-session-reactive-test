package com.ankurpathak.springsessionreactivetest;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomizedReactiveTokenRepositoryImpl extends AbstractCustomizedReactiveDomainRepository<Token, String> implements CustomizedReactiveTokenRepository {
    public CustomizedReactiveTokenRepositoryImpl(ReactiveMongoTemplate template) {
        super(template);
    }

}
