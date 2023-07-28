package com.example.department.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentTest implements Serializable {
    private Long departmentId;
    private String departmentName;
    private int departmentLevel;
    private String departmentSlogan;
    private String departmentType;

}
