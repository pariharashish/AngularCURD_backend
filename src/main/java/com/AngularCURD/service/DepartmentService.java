package com.AngularCURD.service;

import com.AngularCURD.dto.DepartmentRequest;
import com.AngularCURD.entity.Department;
import com.AngularCURD.entity.DepartmentType;
import com.AngularCURD.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository  departmentRepository;

    public DepartmentService(DepartmentRepository repo) {
        this.departmentRepository = repo;
    }

    public List<Department> getAllDepartments() { return departmentRepository.findAll(); }

    public Department createDepartment(DepartmentRequest request) {
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

        return departmentRepository.save(dept);
    }



    public Department updateDepartmentById(Long id, Department newDept) {
        Department dpt = departmentRepository.findById(id).orElseThrow();
        if (newDept.getDeptName() != null) dpt.setDeptName(newDept.getDeptName());
        if (newDept.getDeptTypes() != null) dpt.setDeptTypes(newDept.getDeptTypes());
        return departmentRepository.save(dpt);
    }
    
}
