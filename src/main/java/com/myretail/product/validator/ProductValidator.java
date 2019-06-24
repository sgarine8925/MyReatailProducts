package com.myretail.product.validator;

import com.myretail.product.exception.InputValidationException;
import com.myretail.product.model.Price;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

public final class ProductValidator {

    private ProductValidator() {
        // The class can't be instantiated
    }
    
    /**
     * Validation for product id received.
   	 * @param productId -- Should not be null, not 0
   	 */
    public static void validateProductId(int productId) throws InputValidationException {
        if (productId == 0) {
            throw new InputValidationException("ERR_001", "Product ID is Invalid");
        }
    }

    /**
     * validation for price object
     *
     * @param currentPrice -- currentPrice object can not be null,
     * 						  object value can not be null or 0
     * 						  object currencyCode can not be null or empty
     */
    public static void validateUpdateProductPriceRequest(Price price) throws InputValidationException {
        if (null == price) {
            throw new InputValidationException("ERR_002", "Price is required to update the product current price");
        }

        if (null == price.getValue() || BigDecimal.ZERO.equals(price.getValue())) {
            throw new InputValidationException("ERR_003", "Price value can not be zero");
        }

        if (StringUtils.isEmpty(price.getCurrencyCode())) {
            throw new InputValidationException("ERR_004", "Currency code is required");
        }
    }
}
