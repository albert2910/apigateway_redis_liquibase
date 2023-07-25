package com.example.employee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "employee_age")
    private int employeeAge;

    @Column(name = "employee_login_id")
    private String employeeLoginId;

    @Column(name = "employee_login_password")
    private String employeeLoginPassword;

    @Column(name = "employee_department_id")
    private Long departmentId;

}

