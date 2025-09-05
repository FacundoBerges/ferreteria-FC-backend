package com.ferreteriafc.api.backend.web.exception;

public class PasswordConfirmationMismatchedException extends RuntimeException {

    public PasswordConfirmationMismatchedException(String message) {
        super(message);
    }

}
