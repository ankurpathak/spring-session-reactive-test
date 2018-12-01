package com.ankurpathak.springsessionreactivetest;

import reactor.core.publisher.Mono;

public interface IReactiveTokenService extends IReactiveDomainService<Token, String> {
    Mono<Token> generateToken();

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
