package com.AngularCURD.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptTypeId;

    private String typeName;   // Example: Admin, Support, Technical

    /*@ManyToOne
    @JoinColumn(name = "dept_id")
    @JsonIgnore  // Break the JSON recursion using Jackson Annotations (remove multiple repeats)
    private Department department;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    @JsonBackReference // This corresponds to @JsonManagedReference in Department
    private Department department;
}
