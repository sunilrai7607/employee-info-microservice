package com.sbr.api.rest.service

import com.sbr.api.rest.domain.Employee
import com.sbr.api.rest.exception.EmployeeNotFoundException
import com.sbr.api.rest.repository.EmployeeRepository
import com.sbr.api.rest.service.impl.EmployeeServiceImpl
import spock.lang.Specification


class EmployeeServiceSpec extends Specification {

    EmployeeService employeeService
    def employeeRepository = Mock(EmployeeRepository)
    def employee = Mock(Employee)

    def setup() {
        employeeService = new EmployeeServiceImpl(employeeRepository)
    }


    def "getEmployeeDetails_returnEmployeeInfo"() {
        given: "Mock the findByName"
        employeeRepository.findByName(*_) >> employee
        employee.getName() >> "John"

        when: "call the getEmployee"
        def employee = employeeService.getEmployee("John")

        then: "Validate the name"
        employee.getName() == "John"
    }

    def "getEmployeeDetails_whenNotFound"() {
        given: "Mock the findByName"
        employeeRepository.findByName(*_) >> null

        when: "call the getEmployee"
        employeeService.getEmployee("John")

        then: "Validate the name"
        thrown(EmployeeNotFoundException)
    }
}
