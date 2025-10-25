// EmployeeRepository.java
package com.AngularCURD.Repository;


import com.AngularCURD.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
