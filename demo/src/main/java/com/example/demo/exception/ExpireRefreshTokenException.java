package com.example.demo.exception;


import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ExpireRefreshTokenException extends RuntimeException{
    public ExpireRefreshTokenException(String message, HttpServletResponse response) {
        super(message);
    }
}
