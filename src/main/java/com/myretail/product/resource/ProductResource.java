package com.myretail.product.resource;

import com.myretail.product.model.Price;
import com.myretail.product.model.Product;
import com.myretail.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Satish Garine 6/22/2019
 */
@RestController
@RequestMapping("/products")
@Slf4j
public class ProductResource {

    @Autowired
    private ProductService productService;

    /**
	 * Returns JSON representation of Product resource based on Id specified in URL
	 * @param id
	 * @return Product
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(@PathVariable(name = "id") int productId) {
        log.debug("Request to GET Product By ID: {}", productId);
        Product product = productService.getProductById(productId);
        log.debug("GET Product By ID Response: {}", product);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * to update existing product price based on the price received from request
     *
     * @param currentPrice
     * @param productId
     * @return success
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateProductPrice(@RequestBody Price currentPrice, @PathVariable(name = "id") int productId) {
        log.debug("Request to update current price of the Product: {}", productId);
        productService.updateCurrentPrice(currentPrice, productId);
        log.debug("Update current price of the Product: {} completed", productId);

        return ResponseEntity.ok().build();
    }
}
