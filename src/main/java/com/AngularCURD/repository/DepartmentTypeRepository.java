package com.AngularCURD.repository;

import com.AngularCURD.entity.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentTypeRepository extends JpaRepository<DepartmentType, Long> {

    Optional<DepartmentType> findByTypeNameAndDepartment_DeptName(
            String typeName, String dept_name
    );
   DepartmentType findByTypeName(String typeName);
}
