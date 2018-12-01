package com.ankurpathak.springsessionreactivetest;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class ReactiveTokenService extends AbstractReactiveDomainService<Token, String> implements IReactiveTokenService {

    private final IReactiveTokenRepository dao;

    public ReactiveTokenService(IReactiveTokenRepository dao) {
        super(dao);
        this.dao = dao;
    }


    @Override
    public Mono<Token> generateToken() {
        Token token = Token.getInstance();
        return dao.insert(token)
                .retry(10);
    }


}
