package com.example.demo.exception;

@SuppressWarnings("serial")
public class ExpireAccessTokenException extends RuntimeException{
    public ExpireAccessTokenException(String message) {
        super(message);
    }
}
