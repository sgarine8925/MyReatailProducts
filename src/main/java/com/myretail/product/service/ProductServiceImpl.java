package com.myretail.product.service;

import com.myretail.product.entity.ProductEntity;
import com.myretail.product.exception.BaseException;
import com.myretail.product.exception.RetailBusinessException;
import com.myretail.product.mapper.ProductMapper;
import com.myretail.product.model.Price;
import com.myretail.product.model.Product;
import com.myretail.product.repository.ProductRepository;
import com.myretail.product.rest.client.InventoryClient;
import com.myretail.product.rest.model.Inventory;
import com.myretail.product.validator.ProductValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by Satish Garine 6/22/2019
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryClient inventoryClient;

    /**
   	 * Returns JSON representation of Product resource based on Id specified in URL
   	 * @param productId -- Should not be null, not 0
   	 * @return Product
   	 */
    @Override
    public Product getProductById(int productId) throws BaseException {
        ProductValidator.validateProductId(productId);
        String productName = getProductName(productId);

        ProductEntity productEntity = productRepository.findById(productId);
        if (productEntity == null) {
            throw new RetailBusinessException("ERR_006", "Product is not available in the Storage System", HttpStatus.NO_CONTENT.value());
        }

        Product product = ProductMapper.INSTANCE.convertToModel(productEntity);
        product.setName(productName);

        return product;
    }

    private String getProductName(int productId) throws RetailBusinessException {
        Inventory inventory = inventoryClient.getInventory(productId);

        if (null == inventory
                || null == inventory.getProduct()
                || null == inventory.getProduct().getItem()
                || null == inventory.getProduct().getItem().getProduct_description()
                || StringUtils.isEmpty(inventory.getProduct().getItem().getProduct_description().getTitle())) {
            throw new RetailBusinessException("ERR_005", "Unable to find the product in Inventory Service", HttpStatus.NO_CONTENT.value());
        }

        return inventory.getProduct().getItem().getProduct_description().getTitle();
    }
    /**
     * to update existing product price based on the price received from request
     *
     * @param currentPrice -- currentPrice object can not be null,
     * 						  object value can not be null or 0
     * 						  object currencyCode can not be null or empty
     * @param productId -- cannot be null or 0
     */
    @Override
    public ProductEntity updateCurrentPrice(Price currentPrice, int productId) throws BaseException {

        ProductValidator.validateProductId(productId);
        ProductValidator.validateUpdateProductPriceRequest(currentPrice);

        ProductEntity existingProduct = productRepository.findById(productId);
        if (existingProduct == null) {
            throw new RetailBusinessException("ERR_006", "Product is not available to update", HttpStatus.NO_CONTENT.value());
        }

        log.info("Original Price: {} for product Id: {}", existingProduct.getPrice(), productId);
        existingProduct.setPrice(currentPrice.getValue());
        existingProduct.setCurrency(currentPrice.getCurrencyCode());

        ProductEntity updatedProduct = productRepository.save(existingProduct);
        log.info("Price after update {} for product Id {} : ", updatedProduct.getPrice(), productId);

        return updatedProduct;
    }

}
