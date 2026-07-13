package com.AngularCURD.controller;

import com.AngularCURD.dto.EmployeeRequest;
import com.AngularCURD.entity.Employee;
import com.AngularCURD.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        employeeController = new EmployeeController(employeeService);
    }

    @Test
    void testGetAllEmployees() {
        // Arrange
        Employee emp1 = new Employee();
        emp1.setId(1L);
        emp1.setName("John");

        Employee emp2 = new Employee();
        emp2.setId(2L);
        emp2.setName("Jane");

        List<Employee> employees = Arrays.asList(emp1, emp2);
        when(employeeService.getAllEmployees()).thenReturn(employees);

        // Act - Fixed: Now expects ResponseEntity
        ResponseEntity<List<Employee>> result = employeeController.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size());
        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void testGetEmployeeById() {
        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        // Act - Fixed: Now expects ResponseEntity
        ResponseEntity<Employee> result = employeeController.getById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("John Doe", result.getBody().getName());
        verify(employeeService, times(1)).getEmployeeById(1L);
    }

    @Test
    void testCreateEmployee() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        request.setName("New Employee");
        request.setEmail("new@example.com");
        request.setGender("Male");
        request.setDepartment("IT");

        Employee createdEmployee = new Employee();
        createdEmployee.setId(1L);
        createdEmployee.setName("New Employee");
        createdEmployee.setEmail("new@example.com");

        when(employeeService.createEmployee(any(EmployeeRequest.class))).thenReturn(createdEmployee);

        // Act - Fixed: Now expects ResponseEntity
        ResponseEntity<Employee> result = employeeController.create(request);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("New Employee", result.getBody().getName());
        verify(employeeService, times(1)).createEmployee(any(EmployeeRequest.class));
    }

    @Test
    void testUpdateEmployee() {
        // Arrange
        EmployeeRequest updateRequest = new EmployeeRequest();
        updateRequest.setName("Updated Employee");

        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(1L);
        updatedEmployee.setName("Updated Employee");

        when(employeeService.updateEmployeeById(anyLong(), any(EmployeeRequest.class)))
                .thenReturn(updatedEmployee);

        // Act - Fixed: Now expects ResponseEntity
        ResponseEntity<Employee> result = employeeController.update(1L, updateRequest);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Updated Employee", result.getBody().getName());
        verify(employeeService, times(1)).updateEmployeeById(1L, updateRequest);
    }

    @Test
    void testDeleteEmployee() {
        // Arrange
        ResponseEntity<String> deleteResponse = ResponseEntity.ok("Employee deleted successfully");
        when(employeeService.delete(1L)).thenReturn(deleteResponse);

        // Act - Fixed: Now expects ResponseEntity
        ResponseEntity<String> result = employeeController.delete(1L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody().contains("deleted"));
        verify(employeeService, times(1)).delete(1L);
    }
}
