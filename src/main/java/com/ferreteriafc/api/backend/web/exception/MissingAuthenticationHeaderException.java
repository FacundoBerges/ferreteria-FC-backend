package com.ferreteriafc.api.backend.web.exception;

public class MissingAuthenticationHeaderException extends RuntimeException {

    public MissingAuthenticationHeaderException(String message) {
        super(message);
    }

}
