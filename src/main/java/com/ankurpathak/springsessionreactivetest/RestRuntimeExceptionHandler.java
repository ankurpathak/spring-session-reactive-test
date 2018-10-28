package com.ankurpathak.springsessionreactivetest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
class RestRuntimeExceptionHandler {


    private static final Logger log = LoggerFactory.getLogger(RestRuntimeExceptionHandler.class);


    private final IMessageService messageService;

    public RestRuntimeExceptionHandler(IMessageService messageService) {
        this.messageService = messageService;
    }


    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Mono<?>> handleNotFoundException(NotFoundException ex) {
        log.info("message: {} cause: {}", ex.getMessage(), ex.getCause());
        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        Mono.just(
                                ApiResponse.getInstance(
                                        ex.getCode(),
                                        messageService.getMessage(ApiMessages.NOT_FOUND, ex.getEntity(), ex.getProperty(), ex.getId())
                                )
                        )
                );

    }


}