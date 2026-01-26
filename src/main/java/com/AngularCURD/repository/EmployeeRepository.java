// EmployeeRepository.java
package com.AngularCURD.repository;


import com.AngularCURD.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
