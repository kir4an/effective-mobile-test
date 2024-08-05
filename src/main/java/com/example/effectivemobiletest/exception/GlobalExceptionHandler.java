package com.example.effectivemobiletest.exception;


import com.example.effectivemobiletest.dto.ResponseErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseErrorDTO handleTaskNotFoundException(InvalidCredentialsException invalidCredentialsException){
        return ResponseErrorDTO.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .errorMessage(List.of(invalidCredentialsException.getMessage()))
                .build();
    }
}
