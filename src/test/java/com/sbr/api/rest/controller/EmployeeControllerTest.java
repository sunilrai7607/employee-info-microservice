package com.sbr.api.rest.controller;

import com.sbr.api.rest.domain.Employee;
import com.sbr.api.rest.exception.EmployeeNotFoundException;
import com.sbr.api.rest.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void getEmployee_ShouldReturnEmploy() throws Exception {
        given(employeeService.getEmployee(anyString())).willReturn(new Employee("1", "John"));

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("John"));
    }

    @Test
    public void getEmployee_notFound() throws Exception {
        given(employeeService.getEmployee(anyString())).willThrow(new EmployeeNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/John"))
                .andExpect(status().isNotFound());

    }

}
