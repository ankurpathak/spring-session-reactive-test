package com.ankurpathak.springsessionreactivetest;

public interface IReactiveTokenRepository extends ExtendedReactiveMongoRepository<Token, String>, CustomizedReactiveTokenRepository {
}
