package com.sbr.api.rest.service.impl;

import com.sbr.api.rest.domain.Employee;
import com.sbr.api.rest.exception.EmployeeNotFoundException;
import com.sbr.api.rest.repository.EmployeeRepository;
import com.sbr.api.rest.service.EmployeeService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    /**
     * Method to get the employee by Name
     *
     * @param name
     * @return
     */
    @Override
    @Cacheable("employees")
    public Employee getEmployee(final String name) {
        return Optional.ofNullable(employeeRepository.findByName(name)).orElseThrow(EmployeeNotFoundException::new);
    }
}
