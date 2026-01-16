package com.AngularCURD.Entity;

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

    @ManyToOne
    @JoinColumn(name = "dept_id")
    @JsonIgnore
    private Department departmentId;

    @ManyToOne
    @JoinColumn(name = "dept_type_id")
    @JsonIgnore
    private DepartmentType deptType;

   /* private  Long salary;

    private  String empContactNo;

    @Column(nullable = false, unique = true)
    private String empId;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;*/

}
