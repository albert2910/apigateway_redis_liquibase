package com.example.department.controller;

import com.example.department.common.Level;
import com.example.department.model.Department;
import com.example.department.model.DepartmentTest;
import com.example.department.model.EnumTest;
import com.example.department.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HomeController.class)
class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    private Department department;
    private DepartmentTest departmentTest;

    @BeforeEach
    void setUp() {
        department = new Department(5L, "TRD");
        departmentTest = new DepartmentTest(5L,"TRD",5,"An Cut",EnumTest.TEST.name());
    }

    @Test
    void getAllDepartments() throws Exception{
        when(departmentService.getDepartments()).thenReturn(Collections.singletonList(department));
        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getDepartmentById_whenExistDepartmentId() throws Exception {
        when(departmentService.findDepartmentById(5L)).thenReturn(departmentTest);
        mockMvc.perform(get("/departments/5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

}