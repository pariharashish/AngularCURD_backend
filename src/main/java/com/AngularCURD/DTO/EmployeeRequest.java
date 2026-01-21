package com.AngularCURD.DTO;

import lombok.Data;

@Data
public class EmployeeRequest {
    private String name;
    private String email;
    private String gender;
    private String department;
    private String deptType;
}
