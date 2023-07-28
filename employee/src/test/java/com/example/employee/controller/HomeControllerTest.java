package com.example.employee.controller;

import com.example.employee.dto.EmployeeDTO;
import com.example.employee.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HomeController.class)
class HomeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmployeeService employeeService;

    @Autowired
    ObjectMapper objectMapper;

    EmployeeDTO employeeDTO;
    EmployeeDTO employeeEdit;
    Object department;

    @BeforeEach
    void setUp() {
        employeeDTO = new EmployeeDTO(1L, "Nam Cao", 21, "nam", "123456", department, 1L);
        employeeEdit = new EmployeeDTO(1L, "Nam Cao Vl", 22, "nam", "123456", department, 1L);
    }

    @Test
    void getAllEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(Collections.singletonList(employeeDTO));
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getEmployeeById() throws Exception {
        when(employeeService.findEmployeeById(1L)).thenReturn(employeeDTO);
        mockMvc.perform(get("/employees/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void addEmployee() throws Exception {
        doNothing().when(employeeService).addEmployee(any());
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void editEmployee() throws Exception {
        when(employeeService.editEmployee(any())).thenReturn(employeeEdit);
        mockMvc.perform(put("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeEdit)))
                .andExpect(status().isOk());

    }

    @Test
    void deleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployee(employeeDTO.getEmployeeId());
        mockMvc.perform(delete("/employees/{id}",employeeDTO.getEmployeeId()))
                .andExpect(status().isOk());
        verify(employeeService,atLeast(1)).deleteEmployee(employeeDTO.getEmployeeId());

    }
}