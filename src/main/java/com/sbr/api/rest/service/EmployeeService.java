package com.sbr.api.rest.service;

import com.sbr.api.rest.domain.Employee;

public interface EmployeeService {
    /**
     * Method to get the employee by Name
     *
     * @param name
     * @return
     */
    Employee getEmployee(final String name);
}
