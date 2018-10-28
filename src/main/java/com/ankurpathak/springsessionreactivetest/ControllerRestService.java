package com.ankurpathak.springsessionreactivetest;

import org.springframework.stereotype.Service;

@Service
public class ControllerRestService implements IControllerRestService {
    private final IMessageService messageService;

    public ControllerRestService(IMessageService messageService) {
        this.messageService = messageService;
    }
}
