package com.ankurpathak.springsessionreactivetest;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.hamcrest.Matchers.notNullValue;
import static org.valid4j.Assertive.require;


@Component
public class RegistrationCompleteListener implements ApplicationListener<RegistrationCompleteEvent> {

    private static final Logger log = LoggerFactory.getLogger(RegistrationCompleteListener.class);


    private final IReactiveUserService service;
    private final IReactiveTokenService tokenService;
    private final IEmailService emailService;


    public RegistrationCompleteListener(IReactiveUserService service, IReactiveTokenService tokenService, IEmailService emailService) {
        this.service = service;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }


    @Override
    public void onApplicationEvent(final RegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }
    private void confirmRegistration(final RegistrationCompleteEvent event) {
        require(event, notNullValue());
        tokenService.generateToken()
                .doOnSuccess(token -> {
                    Optional.ofNullable(event.getUser())
                            .ifPresentOrElse(user -> {
                                Optional.ofNullable(user.getEmail())
                                        .ifPresentOrElse(email -> {
                                            email.setTokenId(token.getId());
                                            service.update(user);
                                            emailService.sendForAccountEnable(user, token);
                                        }, () -> log.error("{} {} is null", User.class.getSimpleName(), Params.EMAIL));
                            }, () -> log.error("{} is null", User.class.getSimpleName()));
                })
                .doOnError(ex -> log.error("{} message: {} cause: {}",ex.getClass().getSimpleName(),  ex.getMessage(), ex.getCause()))
                .subscribe();
    }

}
