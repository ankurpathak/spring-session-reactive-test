package com.ankurpathak.springsessionreactivetest;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomizedReactiveRoleRepositoryImpl extends AbstractCustomizedReactiveDomainRepository<Role, String> implements CustomizedReactiveRoleRepository {
    private final ReactiveMongoTemplate template;

    public CustomizedReactiveRoleRepositoryImpl(ReactiveMongoTemplate template) {
        this.template = template;
    }

    @Override
    public ReactiveMongoTemplate getTemplate() {
        return template;
    }
}
