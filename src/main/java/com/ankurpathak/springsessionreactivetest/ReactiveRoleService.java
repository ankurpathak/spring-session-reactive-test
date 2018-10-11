package com.ankurpathak.springsessionreactivetest;

import org.springframework.stereotype.Service;

@Service
public class ReactiveRoleService extends AbstractReactiveDomainService<Role, String> implements IReactiveRoleService {

    private final IReactiveRoleRepository dao;

    public ReactiveRoleService(IReactiveRoleRepository dao) {
        this.dao = dao;
     }


    @Override
    protected ExtendedReactiveMongoRepository<Role, String> getDao() {
        return dao;
    }
}
