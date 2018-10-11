package com.ankurpathak.springsessionreactivetest;

import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface IReactiveUserRepository extends ExtendedReactiveMongoRepository<User, BigInteger>, CustomizedReactiveUserRepository {
}
