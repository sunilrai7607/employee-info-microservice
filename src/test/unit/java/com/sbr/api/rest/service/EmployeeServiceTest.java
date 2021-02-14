package com.sbr.api.rest.service;

import com.sbr.api.rest.domain.Employee;
import com.sbr.api.rest.exception.EmployeeNotFoundException;
import com.sbr.api.rest.repository.EmployeeRepository;
import com.sbr.api.rest.service.impl.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() throws Exception {
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    @Test
    public void getEmployeeDetails_returnEmployeeInfo() {
        //arrange
        given(employeeRepository.findByName(anyString())).willReturn(new Employee("1", "John"));

        //act
        Employee employee = employeeService.getEmployee("John");

        //assert
        assertThat(employee.getName()).isEqualTo("John");
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void getEmployeeDetails_whenNotFound() {
        //arrange
        given(employeeRepository.findByName(anyString())).willReturn(null);

        //act
        employeeService.getEmployee("John");
    }
}