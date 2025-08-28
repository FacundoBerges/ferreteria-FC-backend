package com.ferreteriafc.api.backend.web.exception;

import org.springframework.security.core.AuthenticationException;

public class MissingAuthenticationHeaderException extends AuthenticationException {

    public MissingAuthenticationHeaderException(String message) {
        super(message);
    }

}
