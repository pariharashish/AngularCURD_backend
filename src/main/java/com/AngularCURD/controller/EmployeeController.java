package com.AngularCURD.controller;

import com.AngularCURD.dto.EmployeeRequest;
import com.AngularCURD.entity.Employee;
import com.AngularCURD.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    @Operation(
            summary = "Get All Employee details",
            description = "Retrieves a  All Employee details")
    public List<Employee> getAll() { return service.getAllEmployees(); }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id) { return service.getEmployeeById(id); }

    @PostMapping
    public Employee create(@Valid @RequestBody EmployeeRequest e) { return service.createEmployee(e); }

    @PatchMapping("/{id}")
    public Employee update(@PathVariable Long id, @Valid @RequestBody EmployeeRequest e) {
        return service.updateEmployeeById(id,e);
        }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
