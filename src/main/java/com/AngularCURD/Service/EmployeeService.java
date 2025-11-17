
package com.AngularCURD.Service;

import com.AngularCURD.Entity.Employee;
import com.AngularCURD.Repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public List<Employee> getAll() { return repo.findAll(); }

    public Employee getById(Long id) { return repo.findById(id).orElse(null); }

    public Employee create(Employee employee) { return repo.save(employee); }

    public Employee update(Long id, Employee newEmp) {
        Employee emp = repo.findById(id).orElseThrow();
        if (newEmp.getName() != null) emp.setName(newEmp.getName());
        if (newEmp.getEmail() != null) emp.setEmail(newEmp.getEmail());
        if (newEmp.getDepartment() != null) emp.setDepartment(newEmp.getDepartment());
        if (newEmp.getGender() != null) emp.setGender(newEmp.getGender());
        return repo.save(emp);
    }

    public ResponseEntity<String> delete(Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.ok("Employee with ID " + id + " not found.");
        }
        repo.deleteById(id);
        return ResponseEntity.ok("Employee with ID " + id + " has been deleted successfully.");

    }
}
