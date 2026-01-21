package com.AngularCURD.Controller;

import com.AngularCURD.DTO.DepartmentRequest;
import com.AngularCURD.Entity.Department;
import com.AngularCURD.Service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;}

    @GetMapping
    public List<Department> getAllDepartmentList() {return service.getAll();}

    @PostMapping
    public Department createDepartment(@RequestBody DepartmentRequest d) { return service.createDepartment(d); }

    @PatchMapping("/{id}")
    public Department updateDepartment(@PathVariable Long id, @RequestBody Department d) {
        return service.update(id,d); //
    }

}
