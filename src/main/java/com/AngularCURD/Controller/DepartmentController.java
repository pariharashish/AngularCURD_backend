package com.AngularCURD.Controller;

import com.AngularCURD.Entity.Department;
import com.AngularCURD.Service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {this.service = service;}

    @GetMapping
    public List<Department> getAll() { return service.getAll(); }

    @PostMapping
    public Department create(@RequestBody Department d) { return service.create(d); }


    @PatchMapping("/{id}")
    public Department update(@PathVariable Long id, @RequestBody Department d) {
        return service.update(id,d); //
    }

}
