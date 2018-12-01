package com.ankurpathak.springsessionreactivetest;

public interface IEmailService {
    void sendForAccountEnable(User user, Token token);

    void sendForForgetPassword(User user, Token token);
}
