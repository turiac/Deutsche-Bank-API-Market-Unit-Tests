package com.example.unitTests.advice;

import com.example.unitTests.exception.JWTException;
import com.example.unitTests.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(value = JWTException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleJWTException(JWTException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleJWTExceptionFromFilter(JWTException ex) {
        return new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                ex.getMessage());
    }
}
