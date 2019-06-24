package com.myretail.product.exception;

public class RetailBusinessException extends BaseException {

    public RetailBusinessException(String code) {
        super(code);
    }

    public RetailBusinessException(String code, String developerMessage) {
        super(code, developerMessage);
    }

    public RetailBusinessException(String code, String developerMessage, int httpStatusCode) {
        super(code, developerMessage, httpStatusCode);
    }
}
