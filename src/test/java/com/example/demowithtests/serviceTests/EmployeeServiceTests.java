package com.example.demowithtests.serviceTests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.domain.PassStatus;
import com.example.demowithtests.domain.WorkPass;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.service.employeeService.EmployeeServiceBean;
import com.example.demowithtests.service.workPassService.WorkPassServiceBean;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Employee Service Tests")
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private WorkPassServiceBean workPassService;

    @InjectMocks
    private EmployeeServiceBean employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee
                .builder()
                .id(1)
                .name("Mike")
                .country("UK")
                .email("test@mail.com")
                .isVisible(Boolean.TRUE)
                .gender(Gender.M)
                .workPass(new WorkPass())
                .addresses(new HashSet<>())
                .avatars(new HashSet<>())
                .employeesCabinets(new HashSet<>())
                .build();
    }

    @Test
    @DisplayName("add employee test")
    public void addEmployeeTest() {

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        var created = employeeService.create(employee);

        assertThat(created.getName()).isSameAs(employee.getName());
        verify(employeeRepository).save(employee);
    }

    @Test
    @DisplayName("read employee by id test")
    public void readEmployeeByIdTest() {
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        Employee expected = employeeService.getById(employee.getId());

        assertThat(expected).isSameAs(employee);
        verify(employeeRepository).findById(employee.getId());
    }

    @Test
    @DisplayName("read all employees test")
    public void readAllEmployeesTest() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        var list = employeeRepository.findAll();

        assertThat(list.size()).isGreaterThan(0);
        verify(employeeRepository).findAll();
    }

    @Test
    @DisplayName("delete employee test")
    public void deleteEmployeeTest() {
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        assertEquals(employee.getIsVisible(), Boolean.TRUE);

        employeeService.removeById(employee.getId());

        assertEquals(employee.getIsVisible(), Boolean.FALSE);
        verify(employeeRepository).save(employee);
    }

    @Test
    @DisplayName("add work pass to employee test")
    public void addWorkPassToEmployeeTest() {
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(workPassService.getFree()).thenReturn(new WorkPass());
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        var result = employeeService.addWorkPassToEmployee(employee.getId(), PassStatus.EXPIRED);

        assertEquals(result.getWorkPass().getPassStatus(), PassStatus.ACTIVE);
        assertEquals(result.getWorkPass().getIsFree(), false);
        verify(employeeRepository, times(2)).save(employee);
    }

    @Test
    @DisplayName("delete pass from employee test")
    public void deletePassFromEmployee() {
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        employeeService.deletePassFromEmployee(employee.getId(), PassStatus.EXPIRED);

        assertNull(employee.getWorkPass());
    }

    @Test
    @DisplayName("throw exception when employee not found test")
    public void should_throw_exception_when_employee_doesnt_exist() {
        when(employeeRepository.findById(anyInt())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> employeeRepository.findById(anyInt()));
    }

}
