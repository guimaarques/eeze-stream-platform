package com.eeze.techinterview.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.io.IOException;

@ControllerAdvice
public class ExceptionHandler  {

    @org.springframework.web.bind.annotation.ExceptionHandler({VideoAlreadyExistsException.class})
    public ResponseEntity<Object> videoAlreadyExist(VideoAlreadyExistsException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({VideoNotFoundException.class})
    public ResponseEntity<Object> videoAlreadyExist(VideoNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({IOException.class})
    public ResponseEntity<Object> videoAlreadyExist(IOException exception){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

}
