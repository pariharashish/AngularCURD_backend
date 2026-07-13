package com.AngularCURD.customActuator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Endpoint(id = "customEndpoint")
public class CustomEndpoint {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomEndpoint.class);
    
    @ReadOperation
    public Map<String, Object> readOperation() {
        logger.debug("CustomEndpoint readOperation called");
        return Map.of("message", "Read operation successful", "timestamp", System.currentTimeMillis());
    }
    
    @WriteOperation
    public String writeOperation(@Selector String input) {
        // Added validation
        if (input == null || input.trim().isEmpty()) {
            logger.warn("CustomEndpoint write operation called with empty input");
            return "Error: Input cannot be empty";
        }
        logger.info("CustomEndpoint write operation called with input: {}", input);
        return "Write operation successful with input: " + input;
    }
    
    @DeleteOperation
    public String deleteOperation(@Selector String id) {
        // Added validation
        if (id == null || id.trim().isEmpty()) {
            logger.warn("CustomEndpoint delete operation called with empty id");
            return "Error: ID cannot be empty";
        }
        logger.info("CustomEndpoint delete operation called with id: {}", id);
        return "Delete operation successful for id: " + id;
    }
}
