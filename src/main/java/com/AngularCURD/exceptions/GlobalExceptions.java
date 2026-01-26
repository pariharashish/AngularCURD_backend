package com.AngularCURD.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler
    public ResponseEntity<String> EmployeeNotFound(CustomExceptions customExceptions){
        return new ResponseEntity<>(customExceptions.getMessage(), HttpStatus.NOT_FOUND);
    }
}
