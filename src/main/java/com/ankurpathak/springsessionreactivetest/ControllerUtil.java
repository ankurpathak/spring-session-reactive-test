package com.ankurpathak.springsessionreactivetest;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.*;

import static com.ankurpathak.springsessionreactivetest.Params.*;

import static com.ankurpathak.springsessionreactivetest.Strings.*;


public class ControllerUtil {

    private ControllerUtil(){}

    private static final Logger log = LoggerFactory.getLogger(ControllerUtil.class);



    public static String contextPath(ServerWebExchange exchange) {
        return exchange.getRequest().getPath().contextPath().value();
    }

    public static String currentPath(ServerWebExchange exchange) {
        return exchange.getRequest().getPath().pathWithinApplication().value();
    }

    public static HttpHeaders headers(ServerWebExchange exchange) {
        return exchange.getResponse().getHeaders();
    }


    public static <TDto> Mono<TDto> processValidation(Mono<TDto> tdDto, IMessageService messageService) {
        return processValidation(tdDto, messageService, ApiCode.VALIDATION, ApiMessages.VALIDATION);
    }

    public static <TDto> Mono<TDto> processValidation(Mono<TDto> tdDto, IMessageService messageService, ApiCode code, String message) {
        return tdDto
                .doOnError(ex -> log.error("{} message: {} cause: {}",ex.getClass().getSimpleName(),  ex.getMessage(), ex.getCause()))
                .onErrorMap(e ->
                        Optional.of(e)
                                .filter(WebExchangeBindException.class::isInstance)
                                .map(WebExchangeBindException.class::cast)
                                .map(result ->
                                        new ValidationException(
                                                result,
                                                messageService.getMessage(message),
                                                code
                                        )
                                ).map(Throwable.class::cast)
                                .orElse(e)
                );
    }


    public static void processValidationForFound(BindingResult result, IMessageService messageService, FoundException ex) {
        if (result.hasErrors()) {
            throw new ValidationException(
                    result,
                    messageService.getMessage(ApiMessages.FOUND, ex.getEntity(), ex.getProperty(), ex.getId()),
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


    public static ApiResponse processSuccess(IMessageService messageService, Map<String, Object> extras) {
        return generateApiResponse(messageService, ApiMessages.SUCCESS, ApiCode.SUCCESS).addExtras(extras);
    }



    public static ApiResponse processSuccess(IMessageService messageService) {
        return processSuccess(messageService, Collections.emptyMap());
    }


    public static ApiResponse processTokenStatus(Token.TokenStatus status, String token, IMessageService messageService) {
        switch (status) {
            case VALID:
                return ControllerUtil.processSuccess(messageService);

            case EXPIRED:
                return ControllerUtil.processExpiredToken(token, messageService);
            case INVALID:
            default:
                throw new InvalidException(ApiCode.INVALID_TOKEN, Params.TOKEN, token);
        }
    }

    public static ApiResponse processExpiredToken(String token, IMessageService messageService) {
        return generateApiResponse(messageService, ApiMessages.EXPIRED_TOKEN, ApiCode.EXPIRED_TOKEN, token);
    }


    private static ApiResponse generateApiResponse(IMessageService messageService, String message, ApiCode code, String... args) {
        return ApiResponse.getInstance(
                        code,
                        messageService.getMessage(message, args)
                );
    }


    public static Throwable processValidationForFound(FoundException fEx, IMessageService messageService) {
        if (fEx.getBindingResult().hasErrors()) {
            throw new ValidationException(
                    fEx.getBindingResult(),
                    messageService.getMessage(ApiMessages.FOUND, fEx.getEntity(), fEx.getProperty(), fEx.getId()),
                    fEx.getCode()
            );

        }
        return fEx;
    }


}
