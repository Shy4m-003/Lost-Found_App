package com.pagasi.lostfound.item_service.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class CustomExtensions {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "error", "Not Found",
                        "message", ex.getMessage(),
                        "status", 404
                ),
                HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<Map<String, Object>> invalidOperation(InvalidOperationException ex) {
        return new ResponseEntity<>(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "error", "Claimed Item",
                        "message", ex.getMessage(),
                        "status", 404
                ),
                HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<Map<String, Object>> unauthorized(UnauthorizedActionException ex){
        return new ResponseEntity<>(
               Map.of(
                       "timestamp", LocalDateTime.now(),
                       "error", "Unauthorized",
                       "message", ex.getMessage(),
                       "status", 401
               ),
                HttpStatus.UNAUTHORIZED
        );
    }

}
