package com.AngularCURD.controller;

import com.AngularCURD.dto.DepartmentRequest;
import com.AngularCURD.entity.Department;
import com.AngularCURD.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;}

    @GetMapping
    public List<Department> getAllDepartmentList() {return service.getAllDepartments();}

    @PostMapping
    // Fixed: Added @Valid annotation for request validation
    public Department createDepartment(@Valid @RequestBody DepartmentRequest d) { return service.createDepartment(d); }

    @PatchMapping("/{id}")
    public Department updateDepartment(@PathVariable Long id, @RequestBody Department d) {
        return service.updateDepartmentById(id,d);
    }

}
