package com.ankurpathak.springsessionreactivetest;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.*;

import static com.ankurpathak.springsessionreactivetest.Params.*;

import static com.ankurpathak.springsessionreactivetest.Strings.*;


public class ControllerUtil {


    public static String contextPath(ServerWebExchange exchange){
        return exchange.getRequest().getPath().contextPath().value();
    }

    public static HttpHeaders headers(ServerWebExchange exchange){
        return exchange.getResponse().getHeaders();
    }


    private static void processValidation(BindingResult result, MessageSource messageSource, ApiCode code, String message) {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result,
                    MessageUtil.getMessage(messageSource, message),
                    code
            );
        }
    }


    public static void processValidation(BindingResult result, MessageSource messageSource) {
        processValidation(result, messageSource, ApiCode.VALIDATION, ApiMessages.VALIDATION);
    }


    public static void processValidationForFound(BindingResult result, MessageSource messageSource, FoundException ex) {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result,
                    MessageUtil.getMessage(messageSource, ApiMessages.FOUND, ex.getEntity(), ex.getProperty(), ex.getId()),
                    ex.getCode()
            );
        }
    }

    public static ResponseEntity<?> processError(MessageSource messageSource, Map<String, Object> extras) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse.getInstance(
                                ApiCode.UNKNOWN,
                                MessageUtil.getMessage(messageSource, ApiMessages.UNKNOWN)
                        )
                                .addExtras(extras)
                );

    }


    public static ResponseEntity<?> processSuccess(MessageSource messageSource, HttpStatus code, Map<String, Object> extras) {
        return ResponseEntity.status(code)
                .body(
                        ApiResponse.getInstance(
                                ApiCode.SUCCESS,
                                MessageUtil.getMessage(messageSource, ApiMessages.SUCCESS)
                        ).addExtras(extras)
                );
    }


    public static ResponseEntity<?> processSuccessNoContent() {
        return ResponseEntity.noContent().build();
    }


    public static ResponseEntity<?> processSuccess(MessageSource messageSource) {
        return processSuccess(messageSource, HttpStatus.OK, Collections.emptyMap());
    }

    public static ResponseEntity<?> processSuccess(MessageSource messageSource, Map<String, Object> extras) {
        return processSuccess(messageSource, HttpStatus.OK, extras);
    }

    public static ResponseEntity<?> processSuccessCreated(MessageSource messageSource) {
        return processSuccess(messageSource, HttpStatus.CREATED, Collections.emptyMap());
    }

    public static ResponseEntity<?> processSuccessCreated(MessageSource messageSource, Map<String, Object> extras) {
        return processSuccess(messageSource, HttpStatus.CREATED, extras);
    }


    public static ResponseEntity<?> processTokenStatus(Token.TokenStatus status, String token, MessageSource messageSource) {
        switch (status) {
            case VALID:
                return ControllerUtil.processSuccess(messageSource);

            case EXPIRED:
                return ControllerUtil.processExpiredToken(token, messageSource);
            case INVALID:
            default:
                throw new InvalidException(ApiCode.INVALID_TOKEN, Params.TOKEN, token);
        }
    }

    public static ResponseEntity<?> processExpiredToken(String token, MessageSource messageSource) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiResponse.getInstance(
                        ApiCode.EXPIRED_TOKEN,
                        MessageUtil.getMessage(messageSource, ApiMessages.EXPIRED_TOKEN, token)
                )
        );
    }


}
