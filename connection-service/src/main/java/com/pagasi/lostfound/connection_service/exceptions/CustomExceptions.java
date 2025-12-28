package com.pagasi.lostfound.connection_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptions {
    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<Map<String, Object>> invalidOperation(InvalidOperationException ex) {
        return new ResponseEntity<>(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "error", "Already existing request",
                        "message", ex.getMessage(),
                        "status", 404
                ),
                HttpStatus.NOT_FOUND
        );
    }
}
