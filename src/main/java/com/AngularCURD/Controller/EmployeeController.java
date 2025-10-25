// EmployeeController.java
package com.AngularCURD.Controller;

import com.AngularCURD.Entity.Employee;
import com.AngularCURD.Repository.EmployeeRepository;
import com.AngularCURD.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200") // allow Angular
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;
    @Autowired
    EmployeeRepository repo;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Employee> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id) { return service.getById(id); }

    @PostMapping
    public Employee create(@RequestBody Employee e) { return service.create(e); }

    @PatchMapping("/{id}")
    public Employee update(@PathVariable Long id, @RequestBody Employee e) {
        Employee emp = service.getById(id);
        if (e.getName() != null) emp.setName(e.getName());
        if (e.getEmail() != null) emp.setEmail(e.getEmail());
        if (e.getDepartment() != null) emp.setDepartment(e.getDepartment());
        if (e.getGender() != null) emp.setGender(e.getGender());
        return service.create(emp); // save() also works for update
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.ok("Employee with ID " + id + " not found.");
        }
        service.delete(id);
        return ResponseEntity.ok("Employee with ID " + id + " has been deleted successfully.");
    }
}
