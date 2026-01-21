package com.AngularCURD.Service;

import com.AngularCURD.DTO.DepartmentRequest;
import com.AngularCURD.Entity.Department;
import com.AngularCURD.Entity.DepartmentType;
import com.AngularCURD.Repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository  departmentRepository;

    public DepartmentService(DepartmentRepository repo) {
        this.departmentRepository = repo;
    }

    public List<Department> getAll() { return departmentRepository.findAll(); }

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



    public Department update(Long id, Department newDept) {
        Department dpt = departmentRepository.findById(id).orElseThrow();
        if (newDept.getDeptName() != null) dpt.setDeptName(newDept.getDeptName());
        if (newDept.getDeptTypes() != null) dpt.setDeptTypes(newDept.getDeptTypes());
        return departmentRepository.save(dpt);
    }
    
}
