package com.myretail.product.rest.client;

import com.myretail.product.rest.model.Inventory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;

public class InventoryClientTest {

    @InjectMocks
    private InventoryClient inventoryClient;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Field inventoryGetURL = ReflectionUtils.findField(InventoryClient.class, "inventoryServiceURL");
        inventoryGetURL.setAccessible(true);
        ReflectionUtils.setField(inventoryGetURL, inventoryClient, "https://ext.client.com/inventory/{id}?excludes={exclusions}");

        Field inventoryGetURLExclusions = ReflectionUtils.findField(InventoryClient.class, "getInventoryServiceExclusions");
        inventoryGetURLExclusions.setAccessible(true);
        ReflectionUtils.setField(inventoryGetURLExclusions, inventoryClient, "price_history");
    }

    @Test
    public void testGetInventory() {
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any(), Mockito.anyMap()))
                .thenReturn(responseEntity);

        Inventory inventory = inventoryClient.getInventory(12345);
        Assert.assertNull(inventory);
    }

    @Test
    public void testGetInventory_EmptyContent() {
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).build();
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any(), Mockito.anyMap()))
                .thenReturn(responseEntity);

        Inventory inventory = inventoryClient.getInventory(12345);
        Assert.assertNull(inventory);
    }


    @Test
    public void testGetInventory_Success() {
        ResponseEntity responseEntity = ResponseEntity.ok(new Inventory());
        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any(), Mockito.anyMap()))
                .thenReturn(responseEntity);

        Inventory inventory = inventoryClient.getInventory(12345);
        Assert.assertNotNull(inventory);
    }
}
