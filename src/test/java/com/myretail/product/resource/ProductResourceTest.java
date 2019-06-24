package com.myretail.product.resource;

import com.myretail.product.exception.InputValidationException;
import com.myretail.product.exception.RetailBusinessException;
import com.myretail.product.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductResource.class)
public class ProductResourceTest {

    @Autowired
    private MockMvc mockResource;

    @MockBean
    private ProductService productService;

    @Test
    public void testInvalidGetURI() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/12345");
        mockResource.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testValidGetURI() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/12345");
        mockResource.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void testInvalidPutURI() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/product/12345");
        mockResource.perform(requestBuilder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testValidPutURI() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/products/12345")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"currencyCode\": \"EUR\", \"value\": \"240\"}");
        mockResource.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProduct_InvalidInput() throws Exception {
        Mockito.when(productService.getProductById(Mockito.anyInt())).thenThrow(InputValidationException.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/0");
        mockResource.perform(requestBuilder)
                .andExpect(status().isPreconditionFailed());
    }
}
