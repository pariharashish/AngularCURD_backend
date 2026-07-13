package com.AngularCURD.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionsTest {

    private GlobalExceptions globalExceptions = new GlobalExceptions();

    @Test
    void testHandleRuntimeException() {
        // Arrange
        RuntimeException runtimeException = new RuntimeException("Test error message");

        // Act
        ResponseEntity<String> response = globalExceptions.handleRuntimeException(runtimeException);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Test error message", response.getBody());
    }

    @Test
    void testHandleCustomException() {
        // Arrange
        CustomExceptions customException = new CustomExceptions("Custom error");

        // Act
        ResponseEntity<String> response = globalExceptions.handleCustomException(customException);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Custom error", response.getBody());
    }
}
