package com.ankurpathak.springsessionreactivetest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

public interface IReactiveUserService extends IReactiveDomainService<User, BigInteger> {
    Mono<Map<String, Object>> possibleCandidateKeys(String username);

    Flux<String> possibleContacts(String username);

    Mono<User> byEmail(String email);

    /*

    Optional<User> byCandidateKey(String s);
    void saveEmailToken(User user, Token token);
    Optional<User> byEmail(String email);
    Optional<User> byEmailTokenId(String tokenId);


    void accountEnableEmail(User user, String email);

    Token.TokenStatus accountEnable(String token);

    Token.TokenStatus forgetPasswordEnable(String token);

    Optional<User> byPasswordTokenId(String tokenId);

    void forgotPasswordEmail(User user, String email);

    void savePasswordToken(User user, Token token);

    */
}
