package com.sbr.api.rest

import com.sbr.api.rest.domain.Employee
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationSpec extends Specification {

    @Autowired
    private TestRestTemplate restTemplate

    def "Get employee and return Employee details"() {
        given: "Mock the request"

        when: "Validate the rest endpoint"
        def response = restTemplate
                .getForEntity("/employees/John", Employee.class)
        then: "validate the response."
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND)
    }
}
