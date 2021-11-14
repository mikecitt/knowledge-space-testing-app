package com.platform.kspace.exceptions;

import org.springframework.http.HttpStatus;

public class KSpaceException extends Exception {
    private HttpStatus httpStatus;

    public KSpaceException() {
        super();
        httpStatus = HttpStatus.OK;
    }

    public KSpaceException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
