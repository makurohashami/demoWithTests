package com.example.demowithtests.web;

import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.dto.employee.EmployeeDto;
import com.example.demowithtests.dto.employee.EmployeeIsVisibleDto;
import com.example.demowithtests.dto.employee.EmployeeReadDto;
import com.example.demowithtests.dto.employee.EmployeeUpdateDto;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.util.config.EmployeeMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Employee", description = "Employee API")
public class EmployeeControllerBean implements EmployeeDocumented {

    private final EmployeeService employeeService;

    //Операция сохранения юзера в базу данных
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto requestForSave) {
        var employee = EmployeeMapper.INSTANCE.fromDto(requestForSave);
        var dto = EmployeeMapper.INSTANCE.toDto(employeeService.create(employee));

        return dto;
    }

    //Получение списка юзеров
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsers() {
        return EmployeeMapper.INSTANCE.toListReadDto(employeeService.getAll());
    }

    @GetMapping("/users/p")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> getPage(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        return employeeService.getAllWithPagination(paging)
                .map(EmployeeMapper.INSTANCE::toReadDto);
    }

    //Получения юзера по id
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id) {
        log.debug("getEmployeeById() Controller - start: id = {}", id);
        var employee = employeeService.getById(id);
        log.debug("getById() Controller - to dto start: id = {}", id);
        var dto = EmployeeMapper.INSTANCE.toReadDto(employee);
        log.debug("getEmployeeById() Controller - end: name = {}", dto.name);
        return dto;
    }

    //Обновление юзера
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto refreshEmployee(@PathVariable("id") Integer id, @RequestBody @Valid EmployeeUpdateDto dto) {
        var employee = employeeService.updateById(id, EmployeeMapper.INSTANCE.fromUpdateDto(dto));
        return EmployeeMapper.INSTANCE.toDto(employee);
    }

    //Удаление по id
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable Integer id) {
        employeeService.removeById(id);
    }

    //Удаление всех юзеров
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers() {
        employeeService.removeAll();
    }

    @GetMapping("/users/country")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> findByCountry(@RequestParam(required = false) String country,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "3") int size,
                                               @RequestParam(defaultValue = "") List<String> sortList,
                                               @RequestParam(defaultValue = "DESC") Sort.Direction sortOrder) {
        return employeeService.findByCountryContaining(country, page, size, sortList, sortOrder.toString())
                .map(EmployeeMapper.INSTANCE::toReadDto);
    }

    //-- Методи Олега --\\

    @GetMapping("/users/c")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersC() {
        return employeeService.getAllEmployeeCountry();
    }

    @GetMapping("/users/s")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersSort() {
        return employeeService.getSortCountry();
    }

    @GetMapping("/users/emails")
    @ResponseStatus(HttpStatus.OK)
    public Optional<String> getAllUsersSo() {
        return employeeService.findEmails();
    }

    //-- Мої методи --\\

    @GetMapping("/users/byGenderAndCountry")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> readByGender(@RequestParam Gender gender, @RequestParam String country) {
        var employees = employeeService.getByGender(gender, country);
        return EmployeeMapper.INSTANCE.toListReadDto(employees);
    }

    @GetMapping("/users/active")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> readActiveAddressesByCountry(@RequestParam String country,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        return employeeService.getActiveAddressesByCountry(country, pageable)
                .map(EmployeeMapper.INSTANCE::toReadDto);
    }

    @GetMapping("/users/procVisible")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeIsVisibleDto> getWhereIsVisibleIsNull() {
        var employees = employeeService.selectWhereIsVisibleIsNull();
        var dto = EmployeeMapper.INSTANCE.toListIsVisibleDto(employees);
        return dto;
    }

    @PostMapping("/users/oneKEmployees")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOneThousandEmployees() {
        employeeService.addOneThousandEmployees();
    }

    @PatchMapping("/users/patchUpdate")
    @ResponseStatus(HttpStatus.OK)
    public void updateEmployeePatch(@RequestBody @Valid EmployeeDto dto) {
        var employee = EmployeeMapper.INSTANCE.fromDto(dto);
        employeeService.updateOneKEmployee(employee);
    }

    @PutMapping("/users/putUpdate")
    @ResponseStatus(HttpStatus.OK)
    public void updateEmployeePut(@RequestBody @Valid EmployeeDto dto) {
        var employee = EmployeeMapper.INSTANCE.fromDto(dto);
        employeeService.updateOneKEmployee(employee);
    }

    @GetMapping("/users/expiredPhotos")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getExpiredPhotos() {
        return EmployeeMapper.INSTANCE.toListReadDto(employeeService.findExpiredPhotos());
    }

    @PatchMapping("/users/sendEmailsByExpiredPhotos")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> sendEmailsByExpiredPhotos() {
        return employeeService.sendEmailsWhereExpiredPhotos();
    }

}
