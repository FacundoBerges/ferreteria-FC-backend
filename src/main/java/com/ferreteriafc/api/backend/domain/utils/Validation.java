package com.ferreteriafc.api.backend.domain.utils;

import java.util.regex.Pattern;

import com.ferreteriafc.api.backend.web.exception.InvalidEmailException;
import com.ferreteriafc.api.backend.web.exception.InvalidIdException;
import com.ferreteriafc.api.backend.web.exception.InvalidPasswordException;

public class Validation {

    private static boolean objectIsNull(Object obj) {
        return obj == null;
    }

    private static boolean integerIsNegativeOrZero(int integer) {
        return integer < 1;
    }

    private static boolean isValidEmail(String email) {
        return Pattern.matches(Constant.EMAIL_REGEX_PATTERN, email);
    }

    private static boolean stringHasUpperCaseChar(String string) {
        return Pattern.matches("[A-Z]+", string);
    }

    private static boolean stringHasLowerCaseChar(String string) {
        return Pattern.matches("[a-z]+", string);
    }

    private static boolean stringHasAnyNumber(String string) {
        return Pattern.matches("[0-9]+", string);
    }

    public static void validateId(Integer id) {
        if(objectIsNull(id))
            throw new InvalidIdException("Id cannot be null.");

        if(integerIsNegativeOrZero(id))
            throw new InvalidIdException("Id cannot be less than 1.");
    }

    public static void validateEmail(String email) {
        if (objectIsNull(email))
            throw new InvalidEmailException("Email cannot be null.");

        if (isValidEmail(email))
            throw new InvalidEmailException(
                "Email must have at least a lowercase character, an uppercase character, an '@' between email and email address, and a '.' at the end of the email."
            );
    }

    public static void validatePassword(String password) {
        if (objectIsNull(password))
            throw new InvalidPasswordException("Password cannot be null.");

        if (!stringHasLowerCaseChar(password))
            throw new InvalidPasswordException("Password must have at least a lowercase character.");

        if (!stringHasUpperCaseChar(password))
            throw new InvalidPasswordException("Password must have at least an uppercase character.");

        if (!stringHasAnyNumber(password))
            throw new InvalidPasswordException("Password must have at least a number.");
    }

}
