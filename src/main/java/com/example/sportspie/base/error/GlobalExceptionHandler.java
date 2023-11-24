package com.example.sportspie.base.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleNoSuchElementFoundException(Exception e) {

        final StateResponse stateResponse = StateResponse.builder()
                .code("ERROR")
                .message(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(stateResponse);
    }
}
