package com.AngularCURD.service;

import com.AngularCURD.dto.DepartmentRequest;
import com.AngularCURD.entity.Department;
import com.AngularCURD.entity.DepartmentType;
import com.AngularCURD.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    
    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository repo) {
        this.departmentRepository = repo;
    }

    public List<Department> getAllDepartments() {
        logger.debug("Fetching all departments");
        return departmentRepository.findAll();
    }

    public Department createDepartment(DepartmentRequest request) {
        logger.info("Creating new department: {}", request.getDeptName());
        Department dept = new Department();
        dept.setDeptName(request.getDeptName());

        // Create DepartmentType entities from the string list
        if (request.getDeptTypes() != null && !request.getDeptTypes().isEmpty()) {
            List<DepartmentType> deptTypes = new ArrayList<>();

            for (String typeName : request.getDeptTypes()) {
                DepartmentType type = new DepartmentType();
                type.setTypeName(typeName);
                type.setDepartment(dept); // Set bidirectional relationship
                deptTypes.add(type);
            }

            dept.setDeptTypes(deptTypes);
        }

        Department savedDept = departmentRepository.save(dept);
        logger.info("Department created successfully with ID: {}", savedDept.getDeptId());
        return savedDept;
    }

    public Department updateDepartmentById(Long id, Department newDept) {
        logger.info("Updating department with ID: {}", id);
        Department dpt = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Department not found with ID: {}", id);
                    return new RuntimeException("Department not found with ID: " + id);
                });
        
        if (newDept.getDeptName() != null) dpt.setDeptName(newDept.getDeptName());
        if (newDept.getDeptTypes() != null) dpt.setDeptTypes(newDept.getDeptTypes());
        
        Department updatedDept = departmentRepository.save(dpt);
        logger.info("Department updated successfully with ID: {}", id);
        return updatedDept;
    }
}
