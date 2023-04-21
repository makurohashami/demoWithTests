package com.example.demowithtests.repositoryTests;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.EmployeesCabinets;
import com.example.demowithtests.domain.EmployeesCabinetsKey;
import com.example.demowithtests.repository.EmployeesCabinetsRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Employees Cabinets Repository Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeesCabinetsRepositoryTests {

    @Autowired
    private EmployeesCabinetsRepository repository;

    @Test
    @Order(1)
    @Rollback(value = false)
    @DisplayName("save employees cabinets test")
    public void saveEmployeesCabinets() {
        var entity = EmployeesCabinets
                .builder()
                .id(new EmployeesCabinetsKey(1, 1))
                .employee(
                        Employee
                                .builder()
                                .name("Mark")
                                .country("UK")
                                .build())
                .cabinet(
                        Cabinet
                                .builder()
                                .capacity(1)
                                .freePlacesCount(1)
                                .name("London")
                                .build()
                )
                .isActive(Boolean.TRUE)
                .build();
        var created = repository.save(entity);

        assertThat(created.getIsActive()).isEqualTo(Boolean.TRUE);
        assertThat(created.getCabinet().getCapacity()).isEqualTo(1);
    }

    @Test
    @Order(2)
    @DisplayName("get employees cabinets by id test")
    @Disabled
    public void getEmployeesCabinetsByIdTest() {
        var entity = repository.findById(
                        new EmployeesCabinetsKey(1, 1))
                .orElseThrow();

        assertThat(entity).isNotNull();
        assertThat(entity.getIsActive()).isEqualTo(true);
    }

    @Test
    @Order(3)
    @DisplayName("get employees cabinets list test")
    public void getEmployeesTest() {
        var list = repository.findAll();

        assertThat(list.size()).isGreaterThan(0);
        assertThat(list.get(0).getIsActive()).isEqualTo(true);
    }

    @Test
    @Order(4)
    @DisplayName("update employees cabinets test")
    @Disabled
    public void updateEmployeesCabinetsTest() {
        var entity = repository.findById(
                        new EmployeesCabinetsKey(1, 1))
                .orElseThrow();

        entity.getEmployee().setName("David");
        var updated = repository.save(entity);

        assertThat(updated.getEmployee().getName()).isEqualTo("David");
    }

    @Test
    @Order(5)
    @DisplayName("count active cabinets by cabinet id test")
    public void countActiveCabinetsByCabinetIdTest() {
        var count = repository.selectCountOfActiveOccupiedPlacesInCabinetByHisId(1);

        assertThat(count).isEqualTo(1);
    }

    @Test
    @Order(6)
    @DisplayName("delete employees cabinets test")
    @Disabled
    public void deleteEmployeesCabinetsTest() {
        var entity = repository.findById(
                        new EmployeesCabinetsKey(1, 1))
                .orElseThrow();

        repository.delete(entity);

        var deleted = repository.findById(
                EmployeesCabinetsKey
                        .builder()
                        .employeeId(1)
                        .cabinetId(1)
                        .build());

        assertThat(deleted.isPresent()).isEqualTo(false);
    }

}
