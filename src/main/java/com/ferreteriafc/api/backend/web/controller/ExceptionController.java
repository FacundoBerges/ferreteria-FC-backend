package com.ferreteriafc.api.backend.web.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ferreteriafc.api.backend.web.dto.response.ErrorDTO;
import com.ferreteriafc.api.backend.web.dto.response.ValidationErrorsDTO;
import com.ferreteriafc.api.backend.web.exception.AlreadyExistException;
import com.ferreteriafc.api.backend.web.exception.InvalidIdException;
import com.ferreteriafc.api.backend.web.exception.NotFoundException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(exception = AlreadyExistException.class)
    public ResponseEntity<?> handleEntityAlreadyExists(AlreadyExistException ex){
        var httpStatus = HttpStatus.BAD_REQUEST;
        var response = new ErrorDTO(httpStatus.value(), httpStatus.getReasonPhrase(), ex.getMessage());

        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(exception = NotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(NotFoundException ex){
        var httpStatus = HttpStatus.NOT_FOUND;
        var response = new ErrorDTO(httpStatus.value(), httpStatus.getReasonPhrase(), ex.getMessage());

        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(exception = InvalidIdException.class)
    public ResponseEntity<?> handleInvalidId(InvalidIdException ex){
        var httpStatus = HttpStatus.BAD_REQUEST;
        var response = new ErrorDTO(httpStatus.value(), httpStatus.getReasonPhrase(), ex.getMessage());

        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleArgumentNotValid(MethodArgumentNotValidException ex){
        var httpStatus = HttpStatus.BAD_REQUEST;
        var errorsMap = new HashMap<String, String>();

        ex.getFieldErrors().forEach(fieldError -> {
            errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        var response = new ValidationErrorsDTO(httpStatus.value(), httpStatus.getReasonPhrase(), errorsMap);

        return new ResponseEntity<>(response, httpStatus);
    }

}
