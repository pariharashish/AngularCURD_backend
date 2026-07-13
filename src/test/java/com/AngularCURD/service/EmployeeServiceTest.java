package com.AngularCURD.service;

import com.AngularCURD.dto.EmployeeRequest;
import com.AngularCURD.entity.Department;
import com.AngularCURD.entity.DepartmentType;
import com.AngularCURD.entity.Employee;
import com.AngularCURD.repository.DepartmentRepository;
import com.AngularCURD.repository.DepartmentTypeRepository;
import com.AngularCURD.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentTypeRepository departmentTypeRepository;

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService(employeeRepository, departmentRepository, departmentTypeRepository);
    }

    @Test
    void testGetEmployeeByIdNotFound() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employeeService.getEmployeeById(1L);
        });
        assertTrue(exception.getMessage().contains("Employee not found"));
    }

    @Test
    void testGetEmployeeByIdSuccess() {
        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Act
        Employee result = employeeService.getEmployeeById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testCreateEmployeeSuccess() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        request.setName("Jane Doe");
        request.setEmail("jane@example.com");
        request.setGender("Female");
        request.setDepartment("HR");
        request.setDeptType("Recruitment");

        Department department = new Department();
        department.setId(1L);
        department.setDeptName("HR");

        DepartmentType deptType = new DepartmentType();
        deptType.setId(1L);
        deptType.setTypeName("Recruitment");
        deptType.setDepartment(department);

        when(departmentRepository.findByDeptName("HR")).thenReturn(Optional.of(department));
        when(departmentTypeRepository.findByTypeNameAndDepartment_DeptName("Recruitment", "HR"))
                .thenReturn(Optional.of(deptType));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> {
            Employee emp = invocation.getArgument(0);
            emp.setId(1L);
            return emp;
        });

        // Act
        Employee result = employeeService.createEmployee(request);

        // Assert
        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testCreateEmployeeDepartmentNotFound() {
        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        request.setDepartment("NonExistent");

        when(departmentRepository.findByDeptName("NonExistent")).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employeeService.createEmployee(request);
        });
        assertTrue(exception.getMessage().contains("Department not found"));
    }

    @Test
    void testUpdateEmployeeByIdSuccess() {
        // Arrange
        Employee existingEmployee = new Employee();
        existingEmployee.setId(1L);
        existingEmployee.setName("Old Name");
        existingEmployee.setEmail("old@example.com");
        existingEmployee.setDepartment("HR");

        EmployeeRequest updateRequest = new EmployeeRequest();
        updateRequest.setName("New Name");
        updateRequest.setEmail("new@example.com");
        updateRequest.setDepartment("IT");

        // Fixed: Use correct field name deptId
        Department newDept = new Department();
        newDept.setDeptId(2L);
        newDept.setDeptName("IT");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        when(departmentRepository.findByDeptName("IT")).thenReturn(Optional.of(newDept));
        when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);

        // Act
        Employee result = employeeService.updateEmployeeById(1L, updateRequest);

        // Assert
        assertNotNull(result);
        assertEquals("New Name", result.getName());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployeeByIdNotFound() {
        // Arrange
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        EmployeeRequest updateRequest = new EmployeeRequest();
        updateRequest.setName("New Name");

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            employeeService.updateEmployeeById(99L, updateRequest);
        });
        assertTrue(exception.getMessage().contains("Employee not found"));
    }

    @Test
    void testUpdateEmployeeWithNullDepartmentInUpdate() {
        // Arrange - Tests the fix for null check when deptType is provided but department is null
        Employee existingEmployee = new Employee();
        existingEmployee.setId(1L);
        existingEmployee.setName("Test");

        EmployeeRequest updateRequest = new EmployeeRequest();
        updateRequest.setDeptType("SomeType");
        updateRequest.setDepartment(null); // department is null

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);

        // Act
        Employee result = employeeService.updateEmployeeById(1L, updateRequest);

        // Assert - Should not throw NPE, should just skip department type update
        assertNotNull(result);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testDeleteEmployeeSuccess() {
        // Arrange
        when(employeeRepository.existsById(1L)).thenReturn(true);

        // Act
        ResponseEntity<String> response = employeeService.delete(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("deleted successfully"));
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteEmployeeNotFound() {
        // Arrange
        when(employeeRepository.existsById(99L)).thenReturn(false);

        // Act
        ResponseEntity<String> response = employeeService.delete(99L);

        // Assert - Fixed: Should return 404, not 200
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("not found"));
    }
}
