package com.ferreteriafc.api.backend.domain.utils;

import com.ferreteriafc.api.backend.web.exception.InvalidIdException;

public class Validation {

    private Validation() {}

    private static boolean objectIsNull(Object obj) {
        return obj == null;
    }

    private static boolean integerIsNegativeOrZero(int integer) {
        return integer < 1;
    }

    public static void validateId(Integer id) {
        if(objectIsNull(id))
            throw new InvalidIdException("Id cannot be null.");

        if(integerIsNegativeOrZero(id))
            throw new InvalidIdException("Id cannot be less than 1.");
    }

}
