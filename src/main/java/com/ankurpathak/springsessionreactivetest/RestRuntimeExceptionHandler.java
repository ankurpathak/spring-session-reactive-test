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
        log.error("{} message: {} cause: {}",ex.getClass().getSimpleName(),  ex.getMessage(), ex.getCause());
        ex.printStackTrace();
        return generateExceptionApiResponse(HttpStatus.NOT_FOUND, ex.getCode(), ApiMessages.NOT_FOUND,ex.getEntity(), ex.getProperty(), ex.getId());
    }

    @ExceptionHandler({FoundException.class})
    public ResponseEntity<Mono<?>> handleFoundException(FoundException ex){
        log.error("{} message: {} cause: {}",ex.getClass().getSimpleName(),  ex.getMessage(), ex.getCause());
        ex.printStackTrace();
        return generateExceptionApiResponse(HttpStatus.CONFLICT, ex.getCode(), ApiMessages.NOT_FOUND, ex.getEntity(), ex.getProperty(), ex.getId());
    }



    private ResponseEntity<Mono<?>> generateExceptionApiResponse(HttpStatus status, ApiCode code, String apiMessage, String... args){
        return ResponseEntity.status(status)
                .body(
                        Mono.just(
                                ApiResponse.getInstance(
                                        code,
                                        messageService.getMessage(ApiMessages.NOT_FOUND, args)
                                )
                        )
                );
    }


}