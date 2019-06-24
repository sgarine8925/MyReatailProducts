package com.myretail.product.rest.client;

import com.myretail.product.rest.model.Inventory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InventoryClient {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${inventory.service.get.url}")
    private String inventoryServiceURL;

    @Value("${inventory.service.get.exclusions}")
    private String getInventoryServiceExclusions;

    /**
   	 * Returns Inventory Object for the input product id.  Makes external call to redsky.target.com using RestTemplate
   	 * @param productId
   	 * @return inventory
   	 */
    public Inventory getInventory(int id) {
        Map<String, String> vars = new HashMap<>();
        vars.put("id", String.valueOf(id));
        vars.put("exclusions", getInventoryServiceExclusions);

        Inventory inventory = null;
        try {
            ResponseEntity<Inventory> responseEntity = restTemplate.getForEntity(inventoryServiceURL, Inventory.class, vars);
            if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                inventory = responseEntity.getBody();
            }
        } catch (RestClientException rce) {
            log.error("Error while accessing Inventory Service: {}, Id: {}", inventoryServiceURL, id);
        }

        return inventory;
    }
}
