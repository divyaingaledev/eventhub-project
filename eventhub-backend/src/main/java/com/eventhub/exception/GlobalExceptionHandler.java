package com.eventhub.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * Validation errors
     */
    @ExceptionHandler(
        MethodArgumentNotValidException.class
    )
    public ResponseEntity<Map<String, Object>>
    handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors =
                new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                    errors.put(
                        error.getField(),
                        error.getDefaultMessage()
                    )
                );

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "status",
                HttpStatus.BAD_REQUEST.value()
        );

        response.put(
                "message",
                "Validation failed"
        );

        response.put(
                "errors",
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


    /*
     * Duplicate email / IllegalArgumentException
     */
    @ExceptionHandler(
        IllegalArgumentException.class
    )
    public ResponseEntity<Map<String, Object>>
    handleIllegalArgumentException(
            IllegalArgumentException ex) {

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "status",
                HttpStatus.BAD_REQUEST.value()
        );

        response.put(
                "message",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


    /*
     * Resource not found
     */
    @ExceptionHandler(
        ResourceNotFoundException.class
    )
    public ResponseEntity<Map<String, Object>>
    handleResourceNotFound(
            ResourceNotFoundException ex) {

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "status",
                HttpStatus.NOT_FOUND.value()
        );

        response.put(
                "message",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }


    /*
     * General error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>>
    handleGeneralException(
            Exception ex) {

        ex.printStackTrace();

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "status",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        response.put(
                "message",
                "Something went wrong on the server"
        );

        return ResponseEntity
                .status(
                    HttpStatus.INTERNAL_SERVER_ERROR
                )
                .body(response);
    }
}