package com.myretail.product.validator;

import com.myretail.product.exception.InputValidationException;
import com.myretail.product.model.Price;
import org.junit.Test;

import java.math.BigDecimal;

public class ProductValidatorTest {

    @Test(expected = InputValidationException.class)
    public void testValidateProductId_Failure() {
        ProductValidator.validateProductId(0);
    }

    @Test
    public void testValidateProductId_Success() {
        ProductValidator.validateProductId(12345);
    }

    @Test(expected = InputValidationException.class)
    public void testValidateUpdateProductPrice_NullPrice() {
        ProductValidator.validateUpdateProductPriceRequest(null);
    }

    @Test(expected = InputValidationException.class)
    public void testValidateUpdateProductPrice_PriceZero() {
        Price price = new Price();
        price.setValue(new BigDecimal(0));
        ProductValidator.validateUpdateProductPriceRequest(price);
    }

    @Test(expected = InputValidationException.class)
    public void testValidateUpdateProductPrice_EmptyCurrency() {
        Price price = new Price();
        price.setValue(new BigDecimal(50));
        price.setCurrencyCode("");
        ProductValidator.validateUpdateProductPriceRequest(price);
    }

    @Test
    public void testValidateUpdateProductPrice_Success() {
        Price price = new Price();
        price.setValue(new BigDecimal(50));
        price.setCurrencyCode("USD");
        ProductValidator.validateUpdateProductPriceRequest(price);
    }
}
