package com.example.employee.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
public class EmployeeDTO implements Serializable {
    private Long employeeId;

    private String employeeName;

    private int employeeAge;

    private String employeeLoginId;

    private String employeeLoginPassword;

    private Object department;

    private Long departmentId;

    public EmployeeDTO() {
    }

}
