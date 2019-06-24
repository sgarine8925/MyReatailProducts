package com.myretail.product.exception;

import com.myretail.product.model.Error;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseExceptionHandlerTest {

    private BaseExceptionHandler exceptionHandler = new BaseExceptionHandler();

    @Test
    public void testHandleInputValidationException() {
        ResponseEntity<Error> responseEntity = exceptionHandler.handleInputValidationException(
                new InputValidationException("ERR_001", "Product ID Invalid"));

        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals("ERR_001", responseEntity.getBody().getCode());
    }

    @Test
    public void testHandleInputValidationException_withStatusCode() {
        ResponseEntity<Error> responseEntity = exceptionHandler.handleInputValidationException(
                new InputValidationException("ERR_001", "Product ID Invalid", 400));

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals("ERR_001", responseEntity.getBody().getCode());
    }


    @Test
    public void testHandleRetailBusinessException() {
        ResponseEntity<Error> responseEntity = exceptionHandler.handleRetailBusinessException(
                new RetailBusinessException("ERR_002", "Product Not Found"));

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals("ERR_002", responseEntity.getBody().getCode());
    }

    @Test
    public void testHandleRetailBusinessException_withStatusCode() {
        ResponseEntity<Error> responseEntity = (ResponseEntity<Error>) exceptionHandler.handleRetailBusinessException(
                new RetailBusinessException("ERR_002", "Internal Server Error", 500));

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals("ERR_002", responseEntity.getBody().getCode());
    }
}
