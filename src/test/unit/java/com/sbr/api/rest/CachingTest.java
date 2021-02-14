package com.sbr.api.rest;

import com.sbr.api.rest.config.CachingConfig;
import com.sbr.api.rest.domain.Employee;
import com.sbr.api.rest.repository.EmployeeRepository;
import com.sbr.api.rest.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = CachingConfig.class)
@AutoConfigureTestDatabase
@AutoConfigureCache
public class CachingTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;


    @Test
    public void caching() throws Exception {
        //arrange
        given(employeeRepository.findByName(anyString())).willReturn(new Employee("1", "John"));
        //act
        employeeService.getEmployee("John");

        employeeService.getEmployee("John");

        //assert
        verify(employeeRepository, times(1)).findByName("John");
    }
}
