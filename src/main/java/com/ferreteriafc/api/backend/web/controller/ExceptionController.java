package com.ferreteriafc.api.backend.web.controller;

import com.ferreteriafc.api.backend.web.dto.ErrorResponseDTO;
import com.ferreteriafc.api.backend.web.exception.AlreadyExistException;

import com.ferreteriafc.api.backend.web.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(exception = AlreadyExistException.class)
    public ResponseEntity<?> entityAlreadyExists(AlreadyExistException ex){
        var httpStatus = HttpStatus.BAD_REQUEST;

        var response = new ErrorResponseDTO(httpStatus.value(), httpStatus.getReasonPhrase(), ex.getMessage());

        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(exception = NotFoundException.class)
    public ResponseEntity<?> entityNotFound(NotFoundException ex){
        var httpStatus = HttpStatus.NOT_FOUND;

        var response = new ErrorResponseDTO(httpStatus.value(), httpStatus.getReasonPhrase(), ex.getMessage());

        return new ResponseEntity<>(response, httpStatus);
    }

}
