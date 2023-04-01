package com.example.demowithtests.service.employeeService;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.domain.PassStatus;
import com.example.demowithtests.domain.WorkPass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeService {

    Employee create(Employee employee);

    List<Employee> getAll();

    Page<Employee> getAllWithPagination(Pageable pageable);

    Employee getById(Integer id);

    Employee updateById(Integer id, Employee plane);

    void removeById(Integer id);

    void removeAll();

    /**
     * @param country   Filter for the country if required
     * @param page      number of the page returned
     * @param size      number of entries in each page
     * @param sortList  list of columns to sort on
     * @param sortOrder sort order. Can be ASC or DESC
     * @return Page object with customers after filtering and sorting
     */
    Page<Employee> findByCountryContaining(String country, int page, int size, List<String> sortList, String sortOrder);

    /**
     * Get all the countries of all the employees.
     *
     * @return A list of all the countries that employees are from.
     */
    List<String> getAllEmployeeCountry();

    /**
     * It returns a list of countries sorted by name.
     *
     * @return A list of countries in alphabetical order.
     */
    List<String> getSortCountry();

    Optional<String> findEmails();

    List<Employee> getByGender(Gender gender, String country);

    Page<Employee> getActiveAddressesByCountry(String country, Pageable pageable);

    List<Employee> selectWhereIsVisibleIsNull();

    void addOneThousandEmployees();

    void updateOneKEmployee(Employee employee);

    List<Employee> findExpiredAvatars();

    List<Employee> findWhereAvatarWillExpireSoon();

    Set<String> sendEmailsWhereExpiredPhotos();

    Set<String> sendEmailsWhereAvatarWillExpireSoon();

    Employee saveAvatarToEmployee(Integer id, MultipartFile img) throws IOException;

    void removeEmployeesAvatar(Integer id);

    byte[] findEmployeesAvatar(Integer id) throws Exception;

    Employee addWorkPassToEmployee(Integer id, PassStatus passDeleteStatus);

    Employee addWorkPassToEmployee(Integer id, WorkPass pass, PassStatus passDeleteStatus);

    void deletePassFromEmployee(Integer id, PassStatus passDeleteStatus);

}
