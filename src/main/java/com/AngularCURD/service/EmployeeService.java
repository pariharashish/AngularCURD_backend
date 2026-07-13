
package com.AngularCURD.service;

import com.AngularCURD.dto.EmployeeRequest;
import com.AngularCURD.entity.Department;
import com.AngularCURD.entity.DepartmentType;
import com.AngularCURD.entity.Employee;
import com.AngularCURD.repository.DepartmentRepository;
import com.AngularCURD.repository.DepartmentTypeRepository;
import com.AngularCURD.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository repo;
    private final DepartmentRepository departmentRepository;
    private final DepartmentTypeRepository departmentTypeRepository;


    public EmployeeService(EmployeeRepository repo, DepartmentRepository departmentRepository, DepartmentTypeRepository departmentTypeRepository) {
        this.repo = repo;
        this.departmentRepository = departmentRepository;
        this.departmentTypeRepository = departmentTypeRepository;
    }

    public List<Employee> getAllEmployees() { return repo.findAll(); }

    public Employee getEmployeeById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
    }

    public Employee createEmployee(EmployeeRequest request) {
        Employee emp = new Employee();
        // 1. Check if department exists - Fixed: Use clear variable naming
        Department department = departmentRepository.findByDeptName(request.getDepartment())
                .orElseThrow(() -> new RuntimeException("Department not found: " + request.getDepartment()));

        // 2️⃣ Validate DepartmentType exists under this Department
        DepartmentType deptType = null;
        if (request.getDeptType() != null && !request.getDeptType().isEmpty()) {
            deptType = departmentTypeRepository
                    .findByTypeNameAndDepartment_DeptName(request.getDeptType(), request.getDepartment())
                    .orElseThrow(() -> new RuntimeException(
                                    "Department type " + request.getDeptType() +
                                            " not found under department " + request.getDepartment() ));

        }

        emp.setName(request.getName());
        emp.setEmail(request.getEmail());
        emp.setGender(request.getGender());
        emp.setDepartment(request.getDepartment());
        emp.setDeptType(deptType);
        emp.setDepartmentId(department); // set FK with clear naming
        return repo.save(emp);
    }

    public Employee updateEmployeeById(Long id, EmployeeRequest newEmp) {
        Employee emp = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        // Update employee basic details
        if (newEmp.getName() != null) emp.setName(newEmp.getName());
        if (newEmp.getEmail() != null) emp.setEmail(newEmp.getEmail());
        if (newEmp.getGender() != null) emp.setGender(newEmp.getGender());


        // Update Department if provided
        if (newEmp.getDepartment() != null) {
            Department department = departmentRepository
                    .findByDeptName(newEmp.getDepartment())
                    .orElseThrow(() ->
                            new RuntimeException("Department not found: " + newEmp.getDepartment()));
            emp.setDepartment(newEmp.getDepartment());
            emp.setDepartmentId(department);
        }

        // Update Department Type if provided - Fixed: Add null check for department
        if (newEmp.getDeptType() != null && newEmp.getDepartment() != null) {
            DepartmentType deptType = departmentTypeRepository
                    .findByTypeNameAndDepartment_DeptName(newEmp.getDeptType(), newEmp.getDepartment())
                    .orElseThrow(() -> new RuntimeException(
                            "Department type " + newEmp.getDeptType() +
                                    " not found under department " + newEmp.getDepartment() ));
            emp.setDeptType(deptType);
        }

        return repo.save(emp);
    }

    public ResponseEntity<String> delete(Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee with ID " + id + " not found.");
        }
        repo.deleteById(id);
        return ResponseEntity.ok("Employee with ID " + id + " has been deleted successfully.");

    }
}
