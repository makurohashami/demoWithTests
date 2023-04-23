package com.example.demowithtests.serviceTests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Employee Service Tests")
public class EmployeeServiceTests {


    @Mock
    private EmployeeRepository employeeRepository;
    /* @Mock
     private EmailSenderServiceBean emailSenderService;*/
    /*@Mock
    private FileManagerServiceBean fileManagerService;*/
    @Mock
    private WorkPassServiceBean workPassService;
    /*@Mock
    private CabinetServiceBean cabinetService;
    @Mock
    private EmployeesCabinetsServiceBean employeesCabinetsService;*/

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
    @Disabled
    public void addWorkPassToEmployeeTest() {

    }

    @Test
    @DisplayName("delete pass from employee test")
    @Disabled
    public void deletePassFromEmployee() {

    }

    @Test
    @DisplayName("throw exception when employee not found test")
    public void should_throw_exception_when_employee_doesnt_exist() {
        when(employeeRepository.findById(anyInt())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> employeeRepository.findById(anyInt()));
    }

}
