package com.AngularCURD.DTO;

import lombok.Data;
import java.util.List;

@Data
public class DepartmentRequest {
    private String deptName;
    private List<String> deptTypes;
}
