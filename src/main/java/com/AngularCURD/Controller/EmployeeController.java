package com.AngularCURD.Controller;

import com.AngularCURD.DTO.EmployeeRequest;
import com.AngularCURD.Entity.Employee;
import com.AngularCURD.Service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200") // allow Angular
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;


    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Employee> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id) { return service.getById(id); }

    @PostMapping
    public Employee create(@RequestBody EmployeeRequest e) { return service.create(e); }

    @PatchMapping("/{id}")
    public Employee update(@PathVariable Long id, @RequestBody EmployeeRequest e) {
        return service.update(id,e); //
        }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
