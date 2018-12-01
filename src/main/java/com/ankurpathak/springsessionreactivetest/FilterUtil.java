package com.ankurpathak.springsessionreactivetest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


public class FilterUtil {

    private FilterUtil(){}


    private static final Logger log = LoggerFactory.getLogger(FilterUtil.class);



    public static Mono<Void> generateUnauthorized(ServerWebExchange exchange, Jackson2JsonEncoder encoder, IMessageService messageService, AuthenticationException ex) {
        log.error("{} message: {} cause: {}",ex.getClass().getSimpleName(),  ex.getMessage(), ex.getCause());
        ex.printStackTrace();
        if (ex instanceof DisabledException) {
            return generateApiResponse(exchange, encoder, messageService, HttpStatus.UNAUTHORIZED, ApiCode.ACCOUNT_DISABLED, ApiMessages.ACCOUNT_DISABLED);
        }else {
            return generateApiResponse(exchange, encoder, messageService, HttpStatus.UNAUTHORIZED, ApiCode.UNAUTHORIZED, ApiMessages.UNAUTHORIZED);
        }
    }





    public static Mono<Void> generateSuccess(ServerWebExchange exchange, Jackson2JsonEncoder encoder, IMessageService messageService) {
        return generateApiResponse(exchange, encoder, messageService, HttpStatus.OK, ApiCode.SUCCESS, ApiMessages.SUCCESS);
    }

    public static Mono<Void> generateForbidden(ServerWebExchange exchange, Jackson2JsonEncoder encoder, IMessageService messageService, AccessDeniedException ex) {
        log.error("{} message: {} cause: {}",ex.getClass().getSimpleName(),  ex.getMessage(), ex.getCause());
        ex.printStackTrace();
        return generateApiResponse(exchange, encoder, messageService, HttpStatus.FORBIDDEN, ApiCode.FORBIDDEN, ApiMessages.FORBIDDEN);
    }


    private static Mono<Void> generateApiResponse(ServerWebExchange exchange, Jackson2JsonEncoder encoder, IMessageService messageService, HttpStatus status, ApiCode code, String message){
        if (!exchange.getResponse().isCommitted()) {
            exchange.getResponse().setStatusCode(status);
            WebUtil.setContentTypeApplicationJson(exchange.getResponse().getHeaders());
            return exchange.getResponse().writeWith(
                    MonoUtil.toDataBuffer(
                            exchange.getResponse().bufferFactory(),
                            ApiResponse.getInstance(
                                    code,
                                    messageService.getMessage(message)
                            ),
                            ApiResponse.class,
                            encoder
                    )
            );

        } else {
            return Mono.empty();
        }
    }


}
