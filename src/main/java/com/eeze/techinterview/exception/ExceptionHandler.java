package com.eeze.techinterview.exception;

import com.eeze.techinterview.controller.dto.ControllerExceptionDto;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.io.IOException;

@ControllerAdvice
@Log
public class ExceptionHandler  {

    @org.springframework.web.bind.annotation.ExceptionHandler({VideoAlreadyExistsException.class})
    public ResponseEntity<Object> videoAlreadyExist(VideoAlreadyExistsException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ControllerExceptionDto.of(exception.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({VideoNotFoundException.class})
    public ResponseEntity<Object> videoAlreadyExist(VideoNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ControllerExceptionDto.of(exception.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<Object> invalidRequisition(MissingServletRequestParameterException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ControllerExceptionDto.of(exception.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({VideoException.class})
    public ResponseEntity<Object> videoAlreadyExist(VideoException exception){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ControllerExceptionDto.of(exception.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({HttpMessageNotWritableException.class})
    public void notWritable(HttpMessageNotWritableException exception) {
        log.warning(exception.getMessage());
    }
}
