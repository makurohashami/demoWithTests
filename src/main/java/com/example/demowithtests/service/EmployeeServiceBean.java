package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.repository.EmployeeRepository;
import com.example.demowithtests.util.exception.ResourceIsPrivateException;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import com.example.demowithtests.util.exception.ResourceNotVisibleException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class EmployeeServiceBean implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        log.info("getAll() Service - start:");
        var employees = employeeRepository.findAll();
        employees.stream().forEach(employee -> {
            if (employee.getIsPrivate() == Boolean.TRUE || employee.getIsPrivate() == null)
                setEmployeePrivateFields(employee);
        });
        log.info("setEmployeePrivateFields() Service - end:  = size {}", employees.size());
        return employees;
    }

    private void setEmployeePrivateFields(Employee employee) {
        log.debug("setEmployeePrivateFields() Service - start: id = {}", employee.getId());
        employee.setName("is hidden");
        employee.setEmail("is hidden");
        employee.setCountry("is hidden");
        employee.setAddresses(null);
        employee.setGender(null);
        log.debug("setEmployeePrivateFields() Service - end:  = employee {}", employee);
    }

    @Override
    public Page<Employee> getAllWithPagination(Pageable pageable) {
        log.debug("getAllWithPagination() - start: pageable = {}", pageable);
        Page<Employee> list = employeeRepository.findAll(pageable);
        list.stream().forEach(employee -> {
            if (employee.getIsPrivate() == Boolean.TRUE || employee.getIsPrivate() == null)
                setEmployeePrivateFields(employee);
        });
        log.debug("getAllWithPagination() - end: list = {}", list);
        return list;
    }

    @Override
    public Employee getById(Integer id) {
        log.info("getById(Integer id) Service - start: id = {}", id);
        var employee = employeeRepository.findById(id)
                // .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
                .orElseThrow(ResourceNotFoundException::new);
        changeVisibleStatus(employee);
        changePrivateStatus(employee);
        if (!employee.getIsVisible()) throw new ResourceNotVisibleException();
        if (employee.getIsPrivate()) throw new ResourceIsPrivateException();
        log.info("getById(Integer id) Service - end:  = employee {}", employee);
        return employee;
    }

    private void changePrivateStatus(Employee employee) {
        log.info("changePrivateStatus() Service - start: id = {}", employee.getId());
        if (employee.getIsPrivate() == null) {
            employee.setIsPrivate(Boolean.TRUE);
            employeeRepository.save(employee);
        }
        log.info("changePrivateStatus() Service - end: IsPrivate = {}", employee.getIsPrivate());
    }

    private void changeVisibleStatus(Employee employee) {
        log.info("changeVisibleStatus() Service - start: id = {}", employee.getId());
        if (employee.getIsVisible() == null) {
            employee.setIsVisible(Boolean.TRUE);
            employeeRepository.save(employee);
        }
        log.info("changeVisibleStatus() Service - end: isVisible = {}", employee.getIsVisible());
    }

    @Override
    @Transactional
    public Employee updateById(Integer id, Employee employee) {
        log.info("updateById(Integer id, Employee employee) Service start - id - {}, employee - {}", id, employee);
        return employeeRepository.findById(id)
                .map(entity -> {
                    if (employee.getName() != null && !employee.getName().equals(entity.getName()))
                        entity.setName(employee.getName());
                    if (employee.getEmail() != null && !employee.getEmail().equals(entity.getEmail()))
                        entity.setEmail(employee.getEmail());
                    if (employee.getCountry() != null && !employee.getCountry().equals(entity.getCountry()))
                        entity.setCountry(employee.getCountry());
                    if (employee.getIsPrivate() != null && !employee.getIsPrivate().equals(entity.getIsPrivate()))
                        entity.setIsPrivate(employee.getIsPrivate());
                    log.info("updateById(Integer id, Employee employee) Service end - entity - {}", entity);
                    // TODO: 01.03.2023 isVisible do not update Jira - 5544
                    return employeeRepository.save(entity);
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void removeById(Integer id) {
        //repository.deleteById(id);
        Employee employee = employeeRepository.findById(id)
                // .orElseThrow(() -> new EntityNotFoundException("Employee not found with id = " + id));
                .orElseThrow(ResourceNotFoundException::new);
        employee.setIsVisible(Boolean.FALSE);
        //employeeRepository.delete(employee);
        employeeRepository.save(employee);
    }

    @Override
    public void removeAll() {
        employeeRepository.deleteAll();
    }

    /*@Override
    public Page<Employee> findByCountryContaining(String country, Pageable pageable) {
        return employeeRepository.findByCountryContaining(country, pageable);
    }*/

    @Override
    public Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder) {
        // create Pageable object using the page, size and sort details
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortOrder)));
        // fetch the page object by additionally passing pageable with the filters
        return employeeRepository.findByCountryContaining(country, pageable);
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }

    @Override
    public List<String> getAllEmployeeCountry() {
        log.info("getAllEmployeeCountry() - start:");
        List<Employee> employeeList = employeeRepository.findAll();
        List<String> countries = employeeList.stream()
                .map(country -> country.getCountry())
                .collect(Collectors.toList());
        /*List<String> countries = employeeList.stream()
                .map(Employee::getCountry)
                //.sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());*/

        log.info("getAllEmployeeCountry() - end: countries = {}", countries);
        return countries;
    }

    @Override
    public List<String> getSortCountry() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream()
                .map(Employee::getCountry)
                .filter(c -> c.startsWith("U"))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<String> findEmails() {
        var employeeList = employeeRepository.findAll();

        var emails = employeeList.stream()
                .map(Employee::getEmail)
                .collect(Collectors.toList());

        var opt = emails.stream()
                .filter(s -> s.endsWith(".com"))
                .findFirst()
                .orElse("error?");
        return Optional.ofNullable(opt);
    }

    @Override
    public List<Employee> getByGender(Gender gender, String country) {
        var employees = employeeRepository.findByGender(gender.toString(), country);
        return employees;
    }

    @Override
    public Page<Employee> getActiveAddressesByCountry(String country, Pageable pageable) {
        return employeeRepository.findAllWhereIsActiveAddressByCountry(country, pageable);
    }

    @Override
    public List<Employee> selectWhereIsVisibleIsNull() {
        var employees = employeeRepository.queryEmployeeByIsVisibleIsNull();
        for (Employee employee : employees) employee.setIsVisible(Boolean.TRUE);
        employeeRepository.saveAll(employees);
        return employeeRepository.queryEmployeeByIsVisibleIsNull();
    }

    @Override
    public List<Employee> selectEmployeeByIsPrivateIsNull() {
        var employees = employeeRepository.queryEmployeeByIsPrivateIsNull();
        employees.forEach(employee -> employee.setIsPrivate(Boolean.FALSE));
        employeeRepository.saveAll(employees);
        return employeeRepository.queryEmployeeByIsPrivateIsNull();
    }

    @Override
    public void addOneThousandEmployees() {
        log.info("addOneThousandEmployees() Service - start");
        List<Employee> employees = Collections.nCopies(1000, Employee.builder()
                .name("Yan")
                .email("yan@gmail.com")
                .country("Poland")
                .gender(Gender.M)
                .isPrivate(Boolean.FALSE)
                .isVisible(Boolean.TRUE)
                .build()
        );
        employeeRepository.saveAll(employees);
        log.info("addOneThousandEmployees() Service - end: list size - {}", employees.size());
    }

    @Override
    public void updateOneKEmployee(Employee employee) {
        log.info("updateOneKEmployee() Service - start: employee - {} ", employee);
        Long methodStart = System.currentTimeMillis();
        Integer maxId = employeeRepository.findMaxEmployeeId();
        for (int i = maxId - 999; i <= maxId; ++i) {
            updateById(i, employee);
        }
        Long diff = System.currentTimeMillis() - methodStart;
        log.info("updateOneKEmployee() Service - emd: time in ms - {} ", diff);
    }

}
