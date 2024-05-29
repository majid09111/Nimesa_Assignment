package com.nimesa.assignment.data.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends Exception{

    private final HttpStatus httpStatusCode;

    public ApiException(String message, HttpStatus httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;

    }

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }

}
