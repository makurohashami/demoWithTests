package com.example.demowithtests.web.employeeController;

import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.domain.PassStatus;
import com.example.demowithtests.dto.employee.EmployeeDto;
import com.example.demowithtests.dto.employee.EmployeeIsVisibleDto;
import com.example.demowithtests.dto.employee.EmployeeReadDto;
import com.example.demowithtests.dto.employee.EmployeeUpdateDto;
import com.example.demowithtests.dto.workPass.WorkPassRequest;
import com.example.demowithtests.dto.workPass.WorkPassResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeController {

    EmployeeDto saveEmployee(EmployeeDto requestForSave);

    List<EmployeeReadDto> getAllUsers();

    Page<EmployeeReadDto> getPage(int page, int size);

    EmployeeReadDto getEmployeeById(Integer id);

    EmployeeDto refreshEmployee(Integer id, EmployeeUpdateDto dto);

    void removeEmployeeById(Integer id);

    void removeAllUsers();

    Page<EmployeeReadDto> findByCountry(String country,
                                        int page,
                                        int size,
                                        List<String> sortList,
                                        Sort.Direction sortOrder);

    List<String> getAllUsersC();

    List<String> getAllUsersSort();

    Optional<String> getAllUsersSo();

    List<EmployeeReadDto> readByGender(Gender gender, String country);

    Page<EmployeeReadDto> readActiveAddressesByCountry(String country, int page, int size);

    List<EmployeeIsVisibleDto> getWhereIsVisibleIsNull();

    void createOneThousandEmployees();

    void updateEmployeePatch(EmployeeDto dto);

    void updateEmployeePut(EmployeeDto dto);

    List<EmployeeReadDto> getExpiredAvatars();

    Set<String> sendEmailsByExpiredPhotos();

    EmployeeReadDto addAvatarByEmployeeId(Integer id, MultipartFile avatarImg) throws IOException;

    void deleteAvatarByEmployeeId(Integer id);

    byte[] readAvatarByEmployeeId(Integer id) throws Exception;

    List<EmployeeReadDto> getSoonExpiredAvatars();

    Set<String> sendEmailsBySoonExpiredPhotos();

    EmployeeReadDto addWorkPassToEmployee(Integer id, PassStatus passDeleteStatus);

    EmployeeReadDto addWorkPassToEmployee(Integer id, WorkPassRequest request, PassStatus passDeleteStatus);

    void deletePassFromEmployee(Integer id, PassStatus passDeleteStatus);

    List<WorkPassResponse> getOldPassesOfEmployee(Integer id);

    EmployeeReadDto addEmployeeToCabinet(Integer employeeId, Integer cabinetId);

    void removeEmployeeFromCabinet(Integer employeeId, Integer cabinetId);
}
