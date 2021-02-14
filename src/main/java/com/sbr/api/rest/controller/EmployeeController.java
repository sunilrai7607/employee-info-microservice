package com.sbr.api.rest.controller;

import com.sbr.api.rest.domain.Employee;
import com.sbr.api.rest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees/{name}")
    public Employee getEmployee(@PathVariable final String name) {
        return employeeService.getEmployee(name);
    }
}
