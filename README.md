## Springboot with Test Driven Development (TDD)

Perform following step to build micro service by TDD approach.

Basic principal to TDD approach is AAA

 > `ARRANGE`
    > Mock the calls
>
 > `ACT`
    > Perform action  
> 
 > `ASSERT`
    > Validate the response



1. Create a springBoot application with only StpringBootApplication
```java
@SpringBootApplication
public class EmployeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }

}

```
2. Write Integration Test first
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
