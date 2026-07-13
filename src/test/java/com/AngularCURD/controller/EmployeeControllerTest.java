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
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class) class EmployeeControllerTest {
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
    emp1.setEmail("john@example.com");
    emp1.setGender("Male");
    emp1.setDepartment("IT");

    Employee emp2 = new Employee();
    emp2.setId(2L);
    emp2.setName("Jane");
    emp2.setEmail("jane@example.com");
    emp2.setGender("Female");
    emp2.setDepartment("HR");

    List<Employee> employees = Arrays.asList(emp1, emp2);
    when(employeeService.getAllEmployees()).thenReturn(employees);

    // Act
    ResponseEntity<List<Employee>> response = employeeController.getAll();

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(2, response.getBody().size());
    assertEquals("John", response.getBody().get(0).getName());
    assertEquals("Jane", response.getBody().get(1).getName());
    verify(employeeService, times(1)).getAllEmployees();
}

@Test
void testGetEmployeeById() {
    // Arrange
    Employee employee = new Employee();
    employee.setId(1L);
    employee.setName("John Doe");
    employee.setEmail("john.doe@example.com");
    employee.setGender("Male");
    employee.setDepartment("IT");
    when(employeeService.getEmployeeById(1L)).thenReturn(employee);

    // Act
    ResponseEntity<Employee> response = employeeController.getById(1L);

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("John Doe", response.getBody().getName());
    assertEquals("john.doe@example.com", response.getBody().getEmail());
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
    createdEmployee.setGender("Male");
    createdEmployee.setDepartment("IT");

    when(employeeService.createEmployee(any(EmployeeRequest.class))).thenReturn(createdEmployee);

    // Act
    ResponseEntity<Employee> response = employeeController.create(request);

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("New Employee", response.getBody().getName());
    assertEquals("new@example.com", response.getBody().getEmail());
    assertEquals(1L, response.getBody().getId());
    verify(employeeService, times(1)).createEmployee(any(EmployeeRequest.class));
}

@Test
void testUpdateEmployee() {
    // Arrange
    EmployeeRequest updateRequest = new EmployeeRequest();
    updateRequest.setName("Updated Employee");
    updateRequest.setEmail("updated@example.com");
    updateRequest.setGender("Female");
    updateRequest.setDepartment("HR");

    Employee updatedEmployee = new Employee();
    updatedEmployee.setId(1L);
    updatedEmployee.setName("Updated Employee");
    updatedEmployee.setEmail("updated@example.com");
    updatedEmployee.setGender("Female");
    updatedEmployee.setDepartment("HR");

    when(employeeService.updateEmployeeById(anyLong(), any(EmployeeRequest.class)))
            .thenReturn(updatedEmployee);

    // Act
    ResponseEntity<Employee> response = employeeController.update(1L, updateRequest);

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Updated Employee", response.getBody().getName());
    assertEquals("updated@example.com", response.getBody().getEmail());
    verify(employeeService, times(1)).updateEmployeeById(1L, updateRequest);
}


}