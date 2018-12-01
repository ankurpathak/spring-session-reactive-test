package com.ankurpathak.springsessionreactivetest;

import org.springframework.stereotype.Service;

@Service
public class ReactiveRoleService extends AbstractReactiveDomainService<Role, String> implements IReactiveRoleService {

    private final IReactiveRoleRepository dao;

    public ReactiveRoleService(IReactiveRoleRepository dao) {
        super(dao);
        this.dao = dao;
     }



}
