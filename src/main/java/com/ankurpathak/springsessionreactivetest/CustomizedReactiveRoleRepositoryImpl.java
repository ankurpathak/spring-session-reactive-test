package com.ankurpathak.springsessionreactivetest;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomizedReactiveRoleRepositoryImpl extends AbstractCustomizedReactiveDomainRepository<Role, String> implements CustomizedReactiveRoleRepository {
    public CustomizedReactiveRoleRepositoryImpl(ReactiveMongoTemplate template) {
        super(template);
    }

}
