package com.AngularCURD.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class DepartmentRequest {
    @NotBlank(message = "deptName must not be blank")
    private String deptName;
    
    @NotEmpty(message = "deptTypes must not be empty")
    private List<String> deptTypes;
}
