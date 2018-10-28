package com.ankurpathak.springsessionreactivetest;

import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;

public interface IMessageService {

    String getMessage(String key);
    String getMessage(String key, String... args);
    String getMessage(FieldError error);
}
