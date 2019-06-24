package com.myretail.product.service;

import com.myretail.product.entity.ProductEntity;
import com.myretail.product.exception.InputValidationException;
import com.myretail.product.exception.RetailBusinessException;
import com.myretail.product.model.Price;
import com.myretail.product.repository.ProductRepository;
import com.myretail.product.rest.client.InventoryClient;
import com.myretail.product.rest.model.Inventory;
import com.myretail.product.rest.model.Item;
import com.myretail.product.rest.model.Product;
import com.myretail.product.rest.model.ProductDescription;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("deprecation")
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private InventoryClient inventoryClient;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = InputValidationException.class)
    public void testGetProductById_Zero() {
        productService.getProductById(0);
    }

    @Test(expected = RetailBusinessException.class)
    public void testGetProductById_InventoryFailure() {
        Mockito.when(inventoryClient.getInventory(12345)).thenReturn(null);
        productService.getProductById(12345);
    }

    @Test(expected = RetailBusinessException.class)
    public void testGetProductById_RepositoryFailure() {
        Mockito.when(inventoryClient.getInventory(12345)).thenReturn(mockInventory());
        Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(null);

        productService.getProductById(12345);
    }

    @Test
    public void testGetProductById_Success() {
        Mockito.when(inventoryClient.getInventory(12345)).thenReturn(mockInventory());
        //Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(new ProductEntity());
        Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(mockProductEntity());

        productService.getProductById(12345);
    }


    @Test(expected = InputValidationException.class)
    public void testUpdateProductById_Zero() {
        Price price = new Price();
        price.setValue(new BigDecimal("100"));
        price.setCurrencyCode("USD");
        productService.updateCurrentPrice(price, 0);
    }

    @Test(expected = InputValidationException.class)
    public void testUpdateProductById_InvalidPrice() {
        Price price = new Price();
        price.setValue(new BigDecimal("0"));
        price.setCurrencyCode("USD");
        productService.updateCurrentPrice(price, 12345);
    }

    @Test(expected = InputValidationException.class)
    public void testUpdateProductById_InvalidCurrency() {
        Price price = new Price();
        price.setValue(new BigDecimal("10"));
        price.setCurrencyCode("");
        productService.updateCurrentPrice(price, 12345);
    }

    @Test(expected = RetailBusinessException.class)
    public void testUpdateProductById_RepositoryFailure() {
        Price price = new Price();
        price.setValue(new BigDecimal("10"));
        price.setCurrencyCode("USD");
        Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(null);
        productService.updateCurrentPrice(price, 12345);
    }

    @Test
    public void testUpdateProductById_Success() {
        Price price = new Price();
        price.setValue(new BigDecimal("10"));
        price.setCurrencyCode("USD");
        Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(mockProductEntity(12345, null, null));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(mockProductEntity(12345, new BigDecimal(20), "USD"));
        ProductEntity updatedEntity = productService.updateCurrentPrice(price, 12345);
        assertEquals(new BigDecimal(20), updatedEntity.getPrice());
        assertEquals("USD", updatedEntity.getCurrency());
    }

    private Inventory mockInventory() {
        Inventory inventory = new Inventory();
        Product product = new Product();
        Item item = new Item();
        ProductDescription productDescription = new ProductDescription();
        productDescription.setTitle("Sony Play Station 4");
        item.setProduct_description(productDescription);
        product.setItem(item);
        inventory.setProduct(product);
        return inventory;
    }

    private ProductEntity mockProductEntity(int id, BigDecimal price, String currency) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(id);
        productEntity.setPrice(price);
        productEntity.setCurrency(currency);
        return productEntity;
    }
    
    private ProductEntity mockProductEntity() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1234);
        productEntity.setPrice(new BigDecimal(20));
        productEntity.setCurrency("USD");
        return productEntity;
    }
}
