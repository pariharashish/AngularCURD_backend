package com.AngularCURD.customActuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "applicationInfo")
public class ApplicationInfoEndpoint {
    
    @ReadOperation
    public Map<String, Object> getApplicationInfo() {
        Map<String, Object> info = new HashMap<>();
        
        info.put("applicationName", "Angular CURD Application");
        info.put("version", "1.0.0");
        info.put("description", "Spring Boot REST API with Angular Frontend");
        info.put("startupTime", getStartupTime());
        info.put("currentTime", LocalDateTime.now());
        info.put("environment", System.getProperty("spring.profiles.active", "default"));
        info.put("javaVersion", System.getProperty("java.version"));
        info.put("osName", System.getProperty("os.name"));
        info.put("availableProcessors", Runtime.getRuntime().availableProcessors());
        info.put("maxMemory", Runtime.getRuntime().maxMemory());
        info.put("freeMemory", Runtime.getRuntime().freeMemory());
        info.put("totalMemory", Runtime.getRuntime().totalMemory());
        
        return info;
    }
    
    private LocalDateTime getStartupTime() {
        // You can store this during application startup
        return LocalDateTime.now(); // For demo purposes
    }
}
