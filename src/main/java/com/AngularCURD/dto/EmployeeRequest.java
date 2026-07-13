package com.AngularCURD.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EmployeeRequest {
    @NotBlank(message = "name must not be blank")
    private String name;
    @NotBlank(message = "email must not be blank")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;
    @NotBlank(message = "gender must not be blank")
    private String gender;
    @NotBlank(message = "department must not be blank")
    private String department;
    @NotBlank(message = "deptType must not be blank")
    private String deptType;
}
