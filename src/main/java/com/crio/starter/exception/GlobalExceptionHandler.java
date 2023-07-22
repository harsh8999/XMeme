package com.crio.starter.exception;

import java.util.HashMap;
import java.util.Map;
import com.crio.starter.exchange.ApiResponseExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgsNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        Map<String , String> response = new HashMap<>();

        ex.getBindingResult()
            .getAllErrors()
            .forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                response.put(fieldName, message);
            });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseExceptions>  httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        String message = "Required request body is missing!!!";
        ApiResponseExceptions apiResponse = new ApiResponseExceptions(message);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
