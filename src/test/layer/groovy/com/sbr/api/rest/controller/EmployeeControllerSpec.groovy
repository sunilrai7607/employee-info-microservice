package com.sbr.api.rest.controller


import com.sbr.api.rest.domain.Employee
import com.sbr.api.rest.exception.EmployeeNotFoundException
import com.sbr.api.rest.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Import([MockTestConfig])
@WebMvcTest(controllers = [EmployeeController])
class EmployeeControllerSpec extends Specification {

    @Autowired
    protected MockMvc mockMvc

    @Autowired
    EmployeeService employeeService

    def employee = Mock(Employee)

    def " getEmployee_ShouldReturnEmploy "() {
        given: "Mock the findByName"
        employeeService.getEmployee(*_) >> employee
        employee.getName() >> "John"

        when:
        def response = mockMvc.perform(get("/employees/John"))

        then:
        response.andExpect(status().isOk())
    }

    def "getEmployee_notFound"() {
        given: "Mock the findByName"
        employeeService.getEmployee(*_) >> { throw new EmployeeNotFoundException()}

        when: "Call the endpoint"
        def response = mockMvc.perform(get("/employees/John"))

        then: " verify"
        response.andExpect(status().isNotFound())

    }
}
