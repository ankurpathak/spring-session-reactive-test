package com.ankurpathak.springsessionreactivetest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface CustomizedReactiveUserRepository extends ICustomizedReactiveDomainRepository<User, BigInteger> {

}
