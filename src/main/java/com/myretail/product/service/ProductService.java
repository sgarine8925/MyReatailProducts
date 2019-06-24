package com.myretail.product.service;

import com.myretail.product.entity.ProductEntity;
import com.myretail.product.exception.BaseException;
import com.myretail.product.model.Price;
import com.myretail.product.model.Product;

/**
 * Created by Satish Garine 6/22/2019
 */
public interface ProductService {

    Product getProductById(int id) throws BaseException;

    ProductEntity updateCurrentPrice(Price currentPrice, int productId) throws BaseException;

}
