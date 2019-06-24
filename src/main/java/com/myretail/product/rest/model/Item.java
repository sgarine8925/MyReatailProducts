package com.myretail.product.rest.model;

import lombok.Data;

@Data
public class Item {
    private int tcin;
    private ProductDescription product_description;
}
