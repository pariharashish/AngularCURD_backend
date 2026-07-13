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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Management", description = "Operations related to user management")
public class EmployeeController {

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
        return service.delete(id);
    }
}
