package com.example.filestorage.web.error;

import com.example.filestorage.exceptions.FileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionsHandler {

    @ExceptionHandler({FileNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> notFoundException(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
