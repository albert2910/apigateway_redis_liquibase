package com.example.employee.service;

import com.example.employee.dto.EmployeeDTO;
import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    List<Employee> employeeList;
    EmployeeDTO employeeSave;
    EmployeeDTO employeeEdit;
    Object department;

    @InjectMocks
    @Spy
    EmployeeService employeeService;

    @Mock
    RestTemplate restTemplate;

    @Mock
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeList = Stream.of(new Employee(1L, "Nam Cao", 21, "nam", "111111", 1L),
                        new Employee(2L, "Nam Beo", 23, "nambeo", "111111", 2L))
                .collect(Collectors.toList());
        department = restTemplate.getForObject(anyString(), any(), anyLong());
        employeeSave = new EmployeeDTO(3L, "Cuong Beo", 24, "cuongbeo", "111111", department, 2L);
        employeeEdit = new EmployeeDTO(1L, "Cuong Trau", 24, "cuongbeo", "111111", department, 2L);
    }


    @Test
    void getAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(employeeList);
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for (Employee employee : employeeList) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setDepartment(department);
            employeeDTO.setEmployeeId(employee.getEmployeeId());
            employeeDTO.setEmployeeAge(employee.getEmployeeAge());
            employeeDTO.setEmployeeLoginId(employee.getEmployeeLoginId());
            employeeDTO.setEmployeeLoginPassword(employee.getEmployeeLoginPassword());
            employeeDTO.setEmployeeName(employee.getEmployeeName());
            employeeDTO.setDepartment(department);
            employeeDTOList.add(employeeDTO);
        }
        Assertions.assertEquals(employeeService.getAllEmployees().get(0).getEmployeeId(), employeeDTOList.get(0).getEmployeeId());
        Assertions.assertEquals(2, employeeDTOList.size());

    }

    @Test
    void findEmployeeById() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        Employee employee = employeeList.get(0);
        when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(employee));

        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setEmployeeAge(employee.getEmployeeAge());
        employeeDTO.setEmployeeLoginId(employee.getEmployeeLoginId());
        employeeDTO.setEmployeeLoginPassword(employee.getEmployeeLoginPassword());
        employeeDTO.setEmployeeName(employee.getEmployeeName());
        employeeDTO.setDepartment(department);
        employeeDTO.setDepartmentId(employee.getDepartmentId());
        when(employeeService.findEmployeeById(1L)).thenReturn(employeeDTO);
        EmployeeDTO employeeDTO1 = employeeService.findEmployeeById(1L);
        Assertions.assertEquals(employeeDTO1.getEmployeeName(), employeeDTO.getEmployeeName());
        Assertions.assertNotNull(employeeDTO1);
    }

    @Test
    void addEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setEmployeeName(employeeSave.getEmployeeName());
        employee.setEmployeeAge(employeeSave.getEmployeeAge());
        employee.setEmployeeLoginId(employeeSave.getEmployeeLoginId());
        employee.setEmployeeLoginPassword(employeeSave.getEmployeeLoginPassword());
        employee.setDepartmentId(employeeSave.getDepartmentId());
        employeeService.addEmployee(employeeSave);
        employeeList.add(employee);
        verify(employeeRepository, times(1)).save(any());

    }

//    @Test
//    void addEmployeeS() {
//        doNothing().when(employeeService).addEmployee(any());
//         employeeService.addSuccess(new EmployeeDTO());
//    }

    @Test
    void editEmployee() {
        Employee employee = employeeList.get(0);
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(employee));
        EmployeeDTO employeeDTO = employeeEdit;
        employeeService.editEmployee(employeeDTO);
        Assertions.assertEquals(employeeDTO.getEmployeeId(), employee.getEmployeeId());
        Assertions.assertNotNull(employeeDTO);
        Assertions.assertEquals(employeeList.size(),2);
    }

    @Test
    void deleteEmployee() {
        Employee employee = employeeList.get(0);
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(employee));
        employeeService.deleteEmployee(employee.getEmployeeId());
        verify(employeeRepository).delete(employee);

    }
}