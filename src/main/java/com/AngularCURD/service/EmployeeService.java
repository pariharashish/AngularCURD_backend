package com.AngularCURD.service;

import com.AngularCURD.dto.EmployeeRequest;
import com.AngularCURD.entity.Department;
import com.AngularCURD.entity.DepartmentType;
import com.AngularCURD.entity.Employee;
import com.AngularCURD.repository.DepartmentRepository;
import com.AngularCURD.repository.DepartmentTypeRepository;
import com.AngularCURD.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    
    private final EmployeeRepository repo;
    private final DepartmentRepository departmentRepository;
    private final DepartmentTypeRepository departmentTypeRepository;

    public EmployeeService(EmployeeRepository repo, DepartmentRepository departmentRepository, DepartmentTypeRepository departmentTypeRepository) {
        this.repo = repo;
        this.departmentRepository = departmentRepository;
        this.departmentTypeRepository = departmentTypeRepository;
    }

    public List<Employee> getAllEmployees() {
        logger.debug("Fetching all employees");
        return repo.findAll();
    }

    public Employee getEmployeeById(Long id) {
        logger.debug("Fetching employee with ID: {}", id);
        return repo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Employee not found with ID: {}", id);
                    return new RuntimeException("Employee not found with ID: " + id);
                });
    }

    public Employee createEmployee(EmployeeRequest request) {
        logger.info("Creating new employee: {}", request.getName());
        Employee emp = new Employee();
        
        // Check if department exists
        Department department = departmentRepository.findByDeptName(request.getDepartment())
                .orElseThrow(() -> {
                    logger.warn("Department not found: {}", request.getDepartment());
                    return new RuntimeException("Department not found: " + request.getDepartment());
                });

        // Validate DepartmentType exists under this Department if provided
        DepartmentType deptType = null;
        if (request.getDeptType() != null && !request.getDeptType().isEmpty()) {
            deptType = departmentTypeRepository
                    .findByTypeNameAndDepartment_DeptName(request.getDeptType(), request.getDepartment())
                    .orElseThrow(() -> {
                        logger.warn("Department type {} not found under department {}", request.getDeptType(), request.getDepartment());
                        return new RuntimeException(
                                "Department type " + request.getDeptType() +
                                        " not found under department " + request.getDepartment());
                    });
        }

        emp.setName(request.getName());
        emp.setEmail(request.getEmail());
        emp.setGender(request.getGender());
        emp.setDepartment(request.getDepartment());
        emp.setDeptType(deptType);
        emp.setDepartment_obj(department); // Set FK with clear naming
        
        Employee savedEmp = repo.save(emp);
        logger.info("Employee created successfully with ID: {}", savedEmp.getId());
        return savedEmp;
    }

    public Employee updateEmployeeById(Long id, EmployeeRequest newEmp) {
        logger.info("Updating employee with ID: {}", id);
        Employee emp = repo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Employee not found with ID: {}", id);
                    return new RuntimeException("Employee not found with ID: " + id);
                });

        // Update employee basic details
        if (newEmp.getName() != null) emp.setName(newEmp.getName());
        if (newEmp.getEmail() != null) emp.setEmail(newEmp.getEmail());
        if (newEmp.getGender() != null) emp.setGender(newEmp.getGender());

        // Update Department if provided
        if (newEmp.getDepartment() != null) {
            Department department = departmentRepository
                    .findByDeptName(newEmp.getDepartment())
                    .orElseThrow(() -> {
                        logger.warn("Department not found: {}", newEmp.getDepartment());
                        return new RuntimeException("Department not found: " + newEmp.getDepartment());
                    });
            emp.setDepartment(newEmp.getDepartment());
            emp.setDepartment_obj(department);
        }

        // Update Department Type if provided
        if (newEmp.getDeptType() != null && newEmp.getDepartment() != null) {
            DepartmentType deptType = departmentTypeRepository
                    .findByTypeNameAndDepartment_DeptName(newEmp.getDeptType(), newEmp.getDepartment())
                    .orElseThrow(() -> {
                        logger.warn("Department type {} not found under department {}", newEmp.getDeptType(), newEmp.getDepartment());
                        return new RuntimeException(
                                "Department type " + newEmp.getDeptType() +
                                        " not found under department " + newEmp.getDepartment());
                    });
            emp.setDeptType(deptType);
        }

        Employee updatedEmp = repo.save(emp);
        logger.info("Employee updated successfully with ID: {}", id);
        return updatedEmp;
    }

    public ResponseEntity<String> delete(Long id) {
        logger.info("Deleting employee with ID: {}", id);
        if (!repo.existsById(id)) {
            logger.warn("Employee not found with ID: {} for deletion", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee with ID " + id + " not found.");
        }
        repo.deleteById(id);
        logger.info("Employee deleted successfully with ID: {}", id);
        return ResponseEntity.ok("Employee with ID " + id + " has been deleted successfully.");
    }
}
