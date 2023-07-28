package com.example.department.controller;

import com.example.department.model.Department;
import com.example.department.model.DepartmentTest;
import com.example.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class HomeController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
            return new ResponseEntity<>(departmentService.getDepartments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentTest> getDepartmentById(@PathVariable("id") Long id) {
        DepartmentTest departmentTest = departmentService.findDepartmentById(id);
//        Department department = new Department(departmentTest.getDepartmentId(), departmentTest.getDepartmentName());
            return new ResponseEntity<>(departmentTest, HttpStatus.OK);
    }
}
