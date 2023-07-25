package com.example.department.service;

import com.example.department.model.Department;
import com.example.department.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Cacheable(value = "listDepartments", key = "#root.methodName")
    public List<Department> getDepartments() {
        System.out.println("listDepartments");
        return (List<Department>) departmentRepository.findAll();
    }

    @Cacheable(value = "department", key = "#id")
    public Department findDepartmentById(Long id) {
        System.out.println("department: "+id);
        return departmentRepository.findById(id).get();
    }
}
