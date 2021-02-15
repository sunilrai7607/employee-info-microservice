package com.sbr.api.rest.repository;

import com.sbr.api.rest.domain.Employee;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJdbcTest
@Ignore
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findByName_returnEmployeeDetails() {
        //arrange
        entityManager.persistAndFlush(new Employee("1", "John"));

        //act
        Employee employee = employeeRepository.findByName("John");

        //assert
        assertThat(employee.getName()).isEqualTo("John");

    }
}