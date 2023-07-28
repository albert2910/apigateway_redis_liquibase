package com.example.department.service;

import com.example.department.model.Department;
import com.example.department.model.DepartmentTest;
import com.example.department.model.EnumTest;
import com.example.department.repository.DepartmentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;
    @Mock
    private TestUseEnum testUseEnum;
    @Mock
    TestUseAbstrarctClass testUseAbstrarctClass;

    @Mock
    ObjectMapper objectMapper;


    @Test
    void getDepartments() {
        when(departmentRepository.findAll()).thenReturn(createDepartmentList());
        List<Department> departmentList = departmentService.getDepartments();
        Assertions.assertEquals(2, departmentList.size());
        Assertions.assertFalse(departmentList.isEmpty());
    }

    private List<Department> createDepartmentList() {
        return Stream.of(new Department(1L, "IT"),
                        new Department(2L, "HR"))
                .collect(Collectors.toList());
    }

    @Test
    void findDepartmentById_whenExistDepartmentId() {
        Department department = new Department(3L, "BDS");
        when(departmentRepository.findById(3L)).thenReturn(Optional.of(department));
        when(testUseEnum.returnEnum(anyString())).thenReturn("DEV");
        when(testUseAbstrarctClass.talkSlogan(anyString())).thenReturn("NO BUG");
        DepartmentTest departmentTest = departmentService.findDepartmentById(3L);
        Assertions.assertNotNull(departmentTest);
        Assertions.assertEquals("BDS", departmentTest.getDepartmentName());
    }






}