package com.ankurpathak.springsessionreactivetest;

public interface IReactiveRoleRepository extends ExtendedReactiveMongoRepository<Role, String>, CustomizedReactiveRoleRepository {
}
