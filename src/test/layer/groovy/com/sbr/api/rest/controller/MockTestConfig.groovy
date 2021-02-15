package com.sbr.api.rest.controller

import com.sbr.api.rest.service.EmployeeService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import spock.mock.DetachedMockFactory

class MockTestConfig {

    def factory = new DetachedMockFactory()

    @Bean
    @Primary
    EmployeeService employeeService() {
        factory.Mock(EmployeeService)
    }
}
