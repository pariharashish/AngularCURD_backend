package com.AngularCURD.Service;

import com.AngularCURD.Entity.Department;
import com.AngularCURD.Entity.DepartmentType;
import com.AngularCURD.Repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository  repo;

    public DepartmentService(DepartmentRepository repo) {
        this.repo = repo;
    }

    public List<Department> getAll() { return repo.findAll(); }

    public Department create(Department department) {
        if (department.getDeptTypes() != null) {
            for (DepartmentType type : department.getDeptTypes()) {
                type.setDepartment(department);
            }
        }
        return repo.save(department);
    }

    public Department update(Long id, Department newDept) {
        Department dpt = repo.findById(id).orElseThrow();
        if (newDept.getDeptName() != null) dpt.setDeptName(newDept.getDeptName());
        if (newDept.getDeptTypes() != null) dpt.setDeptTypes(newDept.getDeptTypes());
        return repo.save(dpt);
    }
    
}
