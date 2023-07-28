package com.example.employee.service;

import com.example.employee.dto.EmployeeDTO;
import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import com.google.common.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RestTemplate restTemplate;

    @Cacheable(value = "listEmployees", key = "#root.methodName")
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for (Employee employee : employeeList) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            Object department = restTemplate.getForObject("http://department-service/departments/{id}", Object.class, employee.getDepartmentId());
            employeeDTO.setEmployeeId(employee.getEmployeeId());
            employeeDTO.setEmployeeAge(employee.getEmployeeAge());
            employeeDTO.setEmployeeLoginId(employee.getEmployeeLoginId());
            employeeDTO.setEmployeeLoginPassword(employee.getEmployeeLoginPassword());
            employeeDTO.setEmployeeName(employee.getEmployeeName());
            employeeDTO.setDepartment(department);
            employeeDTOList.add(employeeDTO);
        }
        return employeeDTOList;
    }

    @Cacheable(value = "employee", key = "#id")
    public EmployeeDTO findEmployeeById(Long id) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        Employee employee = employeeRepository.findById(id).get();
        Object department = restTemplate.getForObject("http://department-service/departments/{id}", Object.class, employee.getDepartmentId());
        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setEmployeeAge(employee.getEmployeeAge());
        employeeDTO.setEmployeeLoginId(employee.getEmployeeLoginId());
        employeeDTO.setEmployeeLoginPassword(employee.getEmployeeLoginPassword());
        employeeDTO.setEmployeeName(employee.getEmployeeName());
        employeeDTO.setDepartment(department);
        employeeDTO.setDepartmentId(employee.getDepartmentId());
        return employeeDTO;
    }

//    có thể dùng cache condition nếu dng chung add với edit

    public void addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setEmployeeName(employeeDTO.getEmployeeName());
        employee.setEmployeeAge(employeeDTO.getEmployeeAge());
        employee.setEmployeeLoginId(employeeDTO.getEmployeeLoginId());
        employee.setEmployeeLoginPassword(employeeDTO.getEmployeeLoginPassword());
        employee.setDepartmentId(employeeDTO.getDepartmentId());
        employeeRepository.save(employee);

    }

    //
//    public String addSuccess(EmployeeDTO employeeSave) {
//        //EmployeeDTO employeeSave = new EmployeeDTO(3L, "Cuong Beo", 24, "cuongbeo", "111111", null, 2L);
//        addEmployee(employeeSave);
//        return "success";
//    }
//    @CacheEvict(value = "employee", key = "#employeeDTO.employeeId")
    @CachePut(value = "employee", key = "#employeeDTO.employeeId")
    public EmployeeDTO editEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(employeeDTO.getEmployeeId()).get();
        employee.setEmployeeId(employeeDTO.getEmployeeId());
        employee.setEmployeeName(employeeDTO.getEmployeeName());
        employee.setEmployeeAge(employeeDTO.getEmployeeAge());
        employee.setEmployeeLoginId(employeeDTO.getEmployeeLoginId());
        employee.setEmployeeLoginPassword(employeeDTO.getEmployeeLoginPassword());
        employee.setDepartmentId(employeeDTO.getDepartmentId());
        employeeRepository.save(employee);
        return employeeDTO;
    }

    @CacheEvict(value = "employee", key = "#id")
    public void deleteEmployee(Long id) {
        employeeRepository.delete(employeeRepository.findById(id).get());
    }
}
