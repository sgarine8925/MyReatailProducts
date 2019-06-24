package com.myretail.product.exception;
/**
 * Created by Satish Garine 6/22/2019
 */

import com.myretail.product.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RetailBusinessException.class)
    public ResponseEntity<Error> handleRetailBusinessException(final RetailBusinessException ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<Error> handleInputValidationException(final InputValidationException ex) {
        return buildErrorResponse(ex, HttpStatus.PRECONDITION_FAILED);
    }

    private ResponseEntity<Error> buildErrorResponse(BaseException ex, HttpStatus status) {
        logger.error(ex.getDeveloperMessage(), ex);

        Error error = new Error();
        error.setCode(ex.getCode());
        error.setMessage(ex.getDeveloperMessage());
        int httpStatusCode = ex.getHttpStatusCode();
        if (httpStatusCode == 0) {
            httpStatusCode = status.value();
        }

        return ResponseEntity.status(httpStatusCode).body(error);
    }
}
