package com.AngularCURD.controller;

import com.AngularCURD.dto.DepartmentRequest;
import com.AngularCURD.entity.Department;
import com.AngularCURD.service.DepartmentService;
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
@RequestMapping("/api/departments")
@Tag(name = "Department Management", description = "API endpoints for managing departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all departments", description = "Retrieves a list of all departments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of departments",
                    content = @Content(schema = @Schema(implementation = Department.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Department>> getAllDepartmentList() {
        return ResponseEntity.ok(service.getAllDepartments());
    }

    @PostMapping
    @Operation(summary = "Create new department", description = "Creates a new department with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Department created successfully",
                    content = @Content(schema = @Schema(implementation = Department.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody DepartmentRequest d) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createDepartment(d));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update department", description = "Updates an existing department's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Department updated successfully",
                    content = @Content(schema = @Schema(implementation = Department.class))),
            @ApiResponse(responseCode = "404", description = "Department not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department d) {
        return ResponseEntity.ok(service.updateDepartmentById(id, d));
    }
}
