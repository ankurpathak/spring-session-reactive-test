package com.ankurpathak.springsessionreactivetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
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
    public static Mono<Void> generateUnauthorized(ServerWebExchange exchange, Jackson2JsonEncoder encoder, IMessageService messageService, AuthenticationException ex) {
        if (!exchange.getResponse().isCommitted()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            WebUtil.setContentTypeApplicationJson(exchange.getResponse().getHeaders());
            ApiResponse apiResponse =
                    ApiResponse.getInstance(
                            ApiCode.UNAUTHORIZED,
                            messageService.getMessage(ApiMessages.UNAUTHORIZED)
                    );

            if (ex instanceof DisabledException) {
                apiResponse =
                        ApiResponse.getInstance(
                                ApiCode.ACCOUNT_DISABLED,
                                messageService.getMessage(ApiMessages.ACCOUNT_DISABLED)
                        );
            }

            return exchange.getResponse().writeWith(
                    DataBufferUtil.toDataBuffer(
                            apiResponse,
                            ApiResponse.class,
                            encoder
                    )
            );


        } else {
            return Mono.empty();
        }
    }



    public static Mono<Void> generateUnauthorized(ServerWebExchange exchange, Jackson2JsonEncoder encoder, IMessageService messageService){
        return generateUnauthorized(exchange, encoder, messageService, null);
    }




    public static Mono<Void> generateSuccess(ServerWebExchange exchange, Jackson2JsonEncoder encoder, IMessageService messageService) {
        if (!exchange.getResponse().isCommitted()) {
            exchange.getResponse().setStatusCode(HttpStatus.OK);
            WebUtil.setContentTypeApplicationJson(exchange.getResponse().getHeaders());
            return exchange.getResponse().writeWith(
                    DataBufferUtil.toDataBuffer(
                            ApiResponse.getInstance(
                                    ApiCode.SUCCESS,
                                    messageService.getMessage(ApiMessages.SUCCESS)
                            ),
                            ApiResponse.class,
                            encoder
                    )
            );

        } else {
            return Mono.empty();
        }
    }

    public static Mono<Void> generateForbidden(ServerWebExchange exchange, Jackson2JsonEncoder encoder, IMessageService messageService) {
        if (!exchange.getResponse().isCommitted()) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            WebUtil.setContentTypeApplicationJson(exchange.getResponse().getHeaders());
            return exchange.getResponse().writeWith(
                    DataBufferUtil.toDataBuffer(
                            ApiResponse.getInstance(
                                    ApiCode.FORBIDDEN,
                                    messageService.getMessage(ApiMessages.FORBIDDEN)
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
