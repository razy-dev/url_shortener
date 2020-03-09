package com.musinsa.UrlShortener.exception;

public class OutOfMaxLengthException extends RuntimeException {

    public OutOfMaxLengthException(String message) {
        super(message);
    }

}
