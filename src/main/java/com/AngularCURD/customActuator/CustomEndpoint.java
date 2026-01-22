package com.AngularCURD.customActuator;

import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Endpoint(id = "customEndpoint")
//Custom actuator
public class CustomEndpoint {
    
    @ReadOperation
    public Map<String, Object> readOperation() {
        // Returns data when accessed via GET
        return Map.of("message", "Read operation successful");
    }
    
    @WriteOperation
    public String writeOperation(@Selector String input) {
        // Performs an action when accessed via POST/PUT
        return "Write operation successful with input: " + input;
    }
    
    @DeleteOperation
    public String deleteOperation(@Selector String id) {
        // Performs delete when accessed via DELETE
        return "Delete operation successful for id: " + id;
    }
}
