package com.eeze.techinterview.exception;

public class VideoAlreadyExistsException extends RuntimeException {

    public VideoAlreadyExistsException(String message){
        super(message);
    }
}
