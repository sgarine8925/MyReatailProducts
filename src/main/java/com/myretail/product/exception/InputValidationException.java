package com.myretail.product.exception;

public class InputValidationException extends BaseException {

    public InputValidationException(String code) {
        super(code);
    }

    public InputValidationException(String code, String developerMessage) {
        super(code, developerMessage);
    }

    public InputValidationException(String code, String developerMessage, int httpStatusCode) {
        super(code, developerMessage, httpStatusCode);
    }
}
