package com.example.demowithtests.repositoryTests;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.repository.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Employee Repository Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    @DisplayName("save employee test")
    public void saveEmployee() {
        var employee = Employee.builder()
                .name("Mark")
                .country("England")
                .addresses(new HashSet<>(Set.of(
                        Address
                                .builder()
                                .country("UK")
                                .build())))
                .gender(Gender.M)
                .build();

        employeeRepository.save(employee);

        assertThat(employee.getId()).isGreaterThan(0);
        assertThat(employee.getId()).isEqualTo(1);
        assertThat(employee.getName()).isEqualTo("Mark");
    }

    @Test
    @Order(2)
    @DisplayName("get employee by id test")
    public void getEmployeeByIdTest() {
        var employee = employeeRepository.findById(1).orElseThrow();

        assertThat(employee.getId()).isEqualTo(1);
    }

    @Test
    @Order(3)
    @DisplayName("get employees test")
    public void getEmployeesTest() {
        var employees = employeeRepository.findAll();

        assertThat(employees.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @DisplayName("update employee test")
    public void updateEmployeeTest() {
        var employee = employeeRepository.findById(1).orElseThrow();
        employee.setName("David");
        var updated = employeeRepository.save(employee);

        assertThat(updated.getName()).isEqualTo("David");
    }

    @Test
    @Order(5)
    @DisplayName("find employee by gender test")
    public void findByGenderTest() {
        var employees =
                employeeRepository.findByGender(Gender.M.toString(), "UK");

        assertThat(employees.get(0).getGender()).isEqualTo(Gender.M);
    }

    @Test
    @Order(6)
    @DisplayName("delete employee test")
    public void deleteEmployeeTest() {
        var employee = employeeRepository.findById(1).orElseThrow();
        employeeRepository.delete(employee);
        var deleted = employeeRepository.findById(1);
        assertThat(deleted.isPresent()).isEqualTo(false);
    }

}
