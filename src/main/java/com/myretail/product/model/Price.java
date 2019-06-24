package com.myretail.product.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Created by Satish Garine 6/22/2019
 */
@Data
@EqualsAndHashCode
@ToString
public class Price {
    protected BigDecimal value;
    protected String currencyCode;
}
