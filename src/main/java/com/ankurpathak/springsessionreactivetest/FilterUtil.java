package com.ankurpathak.springsessionreactivetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class FilterUtil {

    /*

    public static void generateForbidden(ServerHttpRequest request,  response, ObjectMapper objectMapper, MessageSource messageSource) throws IOException{
        generateUnauthorized(request, response, objectMapper, messageSource, null);
    }

*/
    public static Mono<Void> generateUnauthorized(ServerWebExchange exchange, ObjectMapper objectMapper, MessageSource messageSource, AuthenticationException ex) {
        if (!exchange.getResponse().isCommitted()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            WebUtil.setContentTypeApplicationJson(exchange.getResponse().getHeaders());
            ApiResponse apiResponse =
                    ApiResponse.getInstance(
                            ApiCode.UNAUTHORIZED,
                            MessageUtil.getMessage(messageSource, ApiMessages.UNAUTHORIZED)
                    );

            if (ex instanceof DisabledException) {
                apiResponse =
                        ApiResponse.getInstance(
                                ApiCode.ACCOUNT_DISABLED,
                                MessageUtil.getMessage(messageSource, ApiMessages.ACCOUNT_DISABLED)
                        );
            }

            return exchange.getResponse().writeWith(
                    DataBufferUtil.toDataBuffer(
                            apiResponse,
                            ApiResponse.class,
                            objectMapper
                    )
            );


        } else {
            return Mono.empty();
        }
    }



    public static Mono<Void> generateUnauthorized(ServerWebExchange exchange, ObjectMapper objectMapper, MessageSource messageSource){
        return generateUnauthorized(exchange, objectMapper, messageSource, null);
    }




    public static Mono<Void> generateSuccess(ServerWebExchange exchange, ObjectMapper objectMapper, MessageSource messageSource) {
        if (!exchange.getResponse().isCommitted()) {
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            WebUtil.setContentTypeApplicationJson(exchange.getResponse().getHeaders());
            return exchange.getResponse().writeWith(
                    DataBufferUtil.toDataBuffer(
                            ApiResponse.getInstance(
                                    ApiCode.SUCCESS,
                                    MessageUtil.getMessage(messageSource, ApiMessages.SUCCESS)
                            ),
                            ApiResponse.class,
                            objectMapper
                    )
            );

        } else {
            return Mono.empty();
        }
    }

    public static Mono<Void> generateForbidden(ServerWebExchange exchange, ObjectMapper objectMapper, MessageSource messageSource) {
        if (!exchange.getResponse().isCommitted()) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            WebUtil.setContentTypeApplicationJson(exchange.getResponse().getHeaders());
            return exchange.getResponse().writeWith(
                    DataBufferUtil.toDataBuffer(
                            ApiResponse.getInstance(
                                    ApiCode.FORBIDDEN,
                                    MessageUtil.getMessage(messageSource, ApiMessages.FORBIDDEN)
                            ),
                            ApiResponse.class,
                            objectMapper
                    )
            );

        } else {
            return Mono.empty();
        }
    }


}
