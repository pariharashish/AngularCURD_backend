package com.AngularCURD.controller;

import com.AngularCURD.dto.EmployeeRequest;
import com.AngularCURD.entity.Employee;
import com.AngularCURD.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Management", description = "API endpoints for managing employees")
public class EmployeeController {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all employees", description = "Retrieves a list of all employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of employees",
                    content = @Content(schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Employee>> getAll() {
        logger.debug("GET /api/employees - Retrieving all employees");
        return ResponseEntity.ok(service.getAllEmployees());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by ID", description = "Retrieves a specific employee by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found",
                    content = @Content(schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        logger.debug("GET /api/employees/{} - Retrieving employee", id);
        return ResponseEntity.ok(service.getEmployeeById(id));
    }

    @PostMapping
    @Operation(summary = "Create new employee", description = "Creates a new employee with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully",
                    content = @Content(schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Employee> create(@Valid @RequestBody EmployeeRequest e) {
        logger.info("POST /api/employees - Creating new employee");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createEmployee(e));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update employee", description = "Updates an existing employee's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated successfully",
                    content = @Content(schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Employee> update(@PathVariable Long id, @Valid @RequestBody EmployeeRequest e) {
        logger.info("PATCH /api/employees/{} - Updating employee", id);
        return ResponseEntity.ok(service.updateEmployeeById(id, e));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee", description = "Deletes an employee by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.info("DELETE /api/employees/{} - Deleting employee", id);
        return service.delete(id);
    }
}
