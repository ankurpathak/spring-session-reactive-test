package com.ankurpathak.springsessionreactivetest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class ControllerService implements IControllerService {
    private final IMessageService messageService;

    public ControllerService(IMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public <TDto> Mono<TDto> processValidation(Mono<TDto> tdDto) {
        return ControllerUtil.processValidation(tdDto, messageService);
    }

    @Override
    public Throwable processValidationForFound(FoundException fEx) {
        return ControllerUtil.processValidationForFound(fEx, messageService);
    }

    @Override
    public ApiResponse processSuccess() {
        return ControllerUtil.processSuccess(messageService);
    }

    @Override
    public ApiResponse processSuccess(Map<String, Object> extras) {
        return ControllerUtil.processSuccess(messageService, extras);
    }

}