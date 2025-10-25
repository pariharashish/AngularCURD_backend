
package com.AngularCURD.Service;


import com.AngularCURD.Entity.Employee;
import com.AngularCURD.Repository.EmployeeRepository;
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
        emp.setName(newEmp.getName());
        emp.setEmail(newEmp.getEmail());
        emp.setDepartment(newEmp.getDepartment());
        return repo.save(emp);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
