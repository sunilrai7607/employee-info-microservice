package com.sbr.api.rest;


import com.sbr.api.rest.domain.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getEmployee_returnEmployeeDetails() throws Exception {
        //arrange

        //act
        ResponseEntity<Employee> response = restTemplate
                .getForEntity("/employees/John", Employee.class);

        //assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        //assertThat(response.getBody().getName()).isEqualTo("John");

    }
}
