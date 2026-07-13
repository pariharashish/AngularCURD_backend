package com.AngularCURD.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    @JsonIgnore
    private Department department_obj; // Fixed: Clearer naming - this is a Department object, not an ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_type_id")
    @JsonIgnore
    private DepartmentType deptType;

}
