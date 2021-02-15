## Springboot with TDD, BDD and SonarQube Configuration

### Junit vs Spock
Spock and Junit both are great testing framework for API Testing.

Spock framework test case write in Groovy. 
Junit - build.gradle
```yaml
testCompile 'junit:junit:4.12'
testImplementation 'org.springframework.boot:spring-boot-starter-test'
```
Spock - build.gradle
```yaml
// Spock framework configuration
testCompile("org.spockframework:spock-core:${spockFrameWorkVersion}")
testCompile("org.spockframework:spock-spring:${spockFrameWorkVersion}")
```

Perform following step to build micro service by TDD approach.

Junit to TDD approach is AAA
```markdown
  `ARRANGE`
    Mock the calls

  `ACT`
     Perform action  
 
  `ASSERT`
     Validate the response
```

Spock framework
```markdown
Given: ""
When: ""
Then: ""
```





1. Create a springBoot application with only StpringBootApplication
```java
@SpringBootApplication
public class EmployeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }

}

```
2. Write Integration in Junit Test first
```java
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
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("John");

    }
}
```

```groovy
//Spock test
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
```
3. Write Controller Test Class and Create Controller during writing Unit Test case. Mock Service Class
```java
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

```

```groovy
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
```
4. Write unit test for Service class and Repository layer
```java
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
```

```groovy
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
```
5. Write Unit test case Repository later with @DataJdbcTest
```java
@RunWith(SpringRunner.class)
@DataJdbcTest
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
```
