package com.myretail.product.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by Satish Garine 6/22/2019
 */
@Data
@ToString
@EqualsAndHashCode
public class Product {

    private int id;
    private String name;
    private Price currentPrice;

}
