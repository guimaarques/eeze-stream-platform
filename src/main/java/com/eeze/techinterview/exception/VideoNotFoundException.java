package com.eeze.techinterview.exception;

public class VideoNotFoundException extends RuntimeException {

    public VideoNotFoundException(String message){
        super(message);
    }
}
