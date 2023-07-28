package com.example.department.service;

import com.example.department.common.Level;
import com.example.department.model.Department;
import com.example.department.model.DepartmentTest;
import com.example.department.model.EnumTest;
import com.example.department.repository.DepartmentRepository;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    TestUseAbstrarctClass testUseAbstrarctClass;
    @Autowired
    TestUseEnum testUseEnum;

    @Cacheable(value = "listDepartments", key = "#root.methodName")
    public List<Department> getDepartments() {
        log.info("listDepartments");
        return (List<Department>) departmentRepository.findAll();
    }

    @Cacheable(value = "departmentTest", key = "#id")
    public DepartmentTest findDepartmentById(Long id) {

        ObjectMapper mapper = new ObjectMapper();


        DepartmentTest departmentTest = new DepartmentTest();
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        Department department;
        if (!departmentOptional.isPresent()) {
              log.info("departmentOptional is null");
        } else {
            department = departmentOptional.get();
            departmentTest.setDepartmentId(department.getDepartmentId());
            departmentTest.setDepartmentName(department.getDepartmentName());

            //call service khac co su dung enum
            departmentTest.setDepartmentType(testUseEnum.returnEnum(EnumTest.DEV.name()));

            //all service khac co su dung static function
            departmentTest.setDepartmentLevel(Level.returnLevel(3, 4));
            //all service khac co su dung abtract
            departmentTest.setDepartmentSlogan(testUseAbstrarctClass.talkSlogan("NO BUG"));
        }
        department= null;
        // convert user object to json string and return it
        try {
            String a = mapper.writeValueAsString(departmentTest);
            String b = mapper.writeValueAsString(department);
            if (null != a && null != b) {
                log.info("value   {}", a);
            }
            if (null == b || null == a) {
                log.info("value   {} {}", a, b);
            }

        } catch (JsonProcessingException | NullPointerException e) {
            e.printStackTrace();
        }
        return departmentTest;
    }

}
