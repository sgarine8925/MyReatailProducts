package com.myretail.product.exception;

import lombok.Data;

@Data
public class BaseException extends RuntimeException {
    private String code;
    private String developerMessage;
    private int httpStatusCode;

    public BaseException(String code) {
        this.code = code;
    }

    public BaseException(String code, String developerMessage) {
        this.code = code;
        this.developerMessage = developerMessage;
    }

    public BaseException(String code, String developerMessage, int httpStatusCode) {
        this.code = code;
        this.developerMessage = developerMessage;
        this.httpStatusCode = httpStatusCode;
    }
}
