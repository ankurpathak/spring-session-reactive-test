package com.ankurpathak.springsessionreactivetest;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface IControllerService {
    <TDto> Mono<TDto> processValidation(Mono<TDto> tdDto);

    Throwable processValidationForFound(FoundException fEx);

    ApiResponse processSuccess();

    ApiResponse processSuccess(Map<String, Object> extras);
}
