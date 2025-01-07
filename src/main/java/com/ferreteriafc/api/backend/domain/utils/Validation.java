package com.ferreteriafc.api.backend.domain.utils;

import com.ferreteriafc.api.backend.web.exception.InvalidIdException;

public class Validation {

    public static void validateId(Integer id) {
        if(id == null)
            throw new InvalidIdException("Id cannot be null.");

        if(id < 1)
            throw new InvalidIdException("Id cannot be less than 1.");
    }

}
