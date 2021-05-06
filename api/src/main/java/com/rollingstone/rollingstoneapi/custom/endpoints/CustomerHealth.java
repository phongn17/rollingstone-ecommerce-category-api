package com.rollingstone.rollingstoneapi.custom.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Endpoint(id = "is-customer-healthy")
public class CustomerHealth {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplate restTemplate;

    @ReadOperation
    public String IsCustomerHealthy() {
        final String uri = "http://localhost:8092/category";

        try {
            String result = restTemplate.getForObject(uri, String.class);
            return "SUCCESS";
        } catch (Exception ex) {
            logger.error("Health Endpoint Failing with: " + ex.getMessage());
            return "FAILURE";
        }
    }

    @WriteOperation
    public void writeOperation(@Selector String name) {
        // perform write operation
    }

    @DeleteOperation
    public void deleteOperation(@Selector String name) {
        // perform write operation
    }
}
