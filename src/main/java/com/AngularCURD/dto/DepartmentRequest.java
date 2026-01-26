package com.AngularCURD.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class DepartmentRequest {
    @NotBlank(message = "deptName must not be blank")
    private String deptName;
    @NotBlank(message = "deptTypes must not be blank")
    private List<String> deptTypes;
}
