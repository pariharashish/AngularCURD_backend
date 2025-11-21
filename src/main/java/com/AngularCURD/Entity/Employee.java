package com.AngularCURD.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String gender;
    private String department;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    @JsonIgnore
    private Department departmentId;

    @ManyToOne
    @JoinColumn(name = "dept_type_id")
    @JsonIgnore
    private DepartmentType deptType;

}
