package com.AngularCURD.customActuator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "applicationInfo")
public class ApplicationInfoEndpoint {
    
    private static final Logger logger = LoggerFactory.getLogger(ApplicationInfoEndpoint.class);
    private final LocalDateTime startupTime;
    
    public ApplicationInfoEndpoint() {
        // Capture actual startup time when bean is created
        this.startupTime = LocalDateTime.now();
        logger.info("Application started at: {}", startupTime);
    }
    
    @ReadOperation
    public Map<String, Object> getApplicationInfo() {
        logger.debug("Retrieving application info");
        Map<String, Object> info = new HashMap<>();
        
        info.put("applicationName", "Angular CURD Application");
        info.put("version", "1.0.0");
        info.put("description", "Spring Boot REST API with Angular Frontend");
        info.put("startupTime", startupTime);
        info.put("currentTime", LocalDateTime.now());
        info.put("environment", System.getProperty("spring.profiles.active", "default"));
        info.put("javaVersion", System.getProperty("java.version"));
        info.put("osName", System.getProperty("os.name"));
        info.put("availableProcessors", Runtime.getRuntime().availableProcessors());
        
        // Calculate used memory instead of exposing all memory values
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        info.put("maxMemory", maxMemory);
        info.put("usedMemory", usedMemory);
        info.put("freeMemory", freeMemory);
        info.put("totalMemory", totalMemory);
        
        return info;
    }
}
