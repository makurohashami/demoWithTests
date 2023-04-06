package com.example.demowithtests.web.employeeController;

import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.domain.PassStatus;
import com.example.demowithtests.dto.employee.EmployeeDto;
import com.example.demowithtests.dto.employee.EmployeeIsVisibleDto;
import com.example.demowithtests.dto.employee.EmployeeReadDto;
import com.example.demowithtests.dto.employee.EmployeeUpdateDto;
import com.example.demowithtests.dto.workPass.WorkPassRequest;
import com.example.demowithtests.dto.workPass.WorkPassResponse;
import com.example.demowithtests.service.employeeService.EmployeeService;
import com.example.demowithtests.util.mappers.EmployeeMapper;
import com.example.demowithtests.util.mappers.WorkPassMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeControllerBean implements EmployeeDocumented {

    private final EmployeeService employeeService;


    //Операция сохранения юзера в базу данных
    @Override
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto requestForSave) {
        var employee = EmployeeMapper.INSTANCE.fromDto(requestForSave);
        var dto = EmployeeMapper.INSTANCE.toDto(employeeService.create(employee));

        return dto;
    }

    //Получение списка юзеров
    @Override
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getAllUsers() {
        return EmployeeMapper.INSTANCE.toListReadDto(employeeService.getAll());
    }

    @Override
    @GetMapping("/users/p")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> getPage(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        return employeeService.getAllWithPagination(paging)
                .map(EmployeeMapper.INSTANCE::toReadDto);
    }

    //Получения юзера по id
    @Override
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeReadDto getEmployeeById(@PathVariable Integer id) {
        var employee = employeeService.getById(id);
        var dto = EmployeeMapper.INSTANCE.toReadDto(employee);
        return dto;
    }

    //Обновление юзера
    @Override
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto refreshEmployee(@PathVariable("id") Integer id, @RequestBody @Valid EmployeeUpdateDto dto) {
        var employee = employeeService.updateById(id, EmployeeMapper.INSTANCE.fromUpdateDto(dto));
        return EmployeeMapper.INSTANCE.toDto(employee);
    }

    //Удаление по id
    @Override
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeById(@PathVariable Integer id) {
        employeeService.removeById(id);
    }

    //Удаление всех юзеров
    @Override
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllUsers() {
        employeeService.removeAll();
    }

    @Override
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

    @Override
    @GetMapping("/users/c")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersC() {
        return employeeService.getAllEmployeeCountry();
    }

    @Override
    @GetMapping("/users/s")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllUsersSort() {
        return employeeService.getSortCountry();
    }

    @Override
    @GetMapping("/users/emails")
    @ResponseStatus(HttpStatus.OK)
    public Optional<String> getAllUsersSo() {
        return employeeService.findEmails();
    }

    //-- Мої методи --\\

    @Override
    @GetMapping("/users/byGenderAndCountry")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> readByGender(@RequestParam Gender gender, @RequestParam String country) {
        var employees = employeeService.getByGender(gender, country);
        return EmployeeMapper.INSTANCE.toListReadDto(employees);
    }

    @Override
    @GetMapping("/users/active")
    @ResponseStatus(HttpStatus.OK)
    public Page<EmployeeReadDto> readActiveAddressesByCountry(@RequestParam String country,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        return employeeService.getActiveAddressesByCountry(country, pageable)
                .map(EmployeeMapper.INSTANCE::toReadDto);
    }

    @Override
    @GetMapping("/users/procVisible")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeIsVisibleDto> getWhereIsVisibleIsNull() {
        var employees = employeeService.selectWhereIsVisibleIsNull();
        var dto = EmployeeMapper.INSTANCE.toListIsVisibleDto(employees);
        return dto;
    }

    @Override
    @PostMapping("/users/oneKEmployees")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOneThousandEmployees() {
        employeeService.addOneThousandEmployees();
    }

    @Override
    @PatchMapping("/users/patchUpdate")
    @ResponseStatus(HttpStatus.OK)
    public void updateEmployeePatch(@RequestBody @Valid EmployeeDto dto) {
        var employee = EmployeeMapper.INSTANCE.fromDto(dto);
        employeeService.updateOneKEmployee(employee);
    }

    @Override
    @PutMapping("/users/putUpdate")
    @ResponseStatus(HttpStatus.OK)
    public void updateEmployeePut(@RequestBody @Valid EmployeeDto dto) {
        var employee = EmployeeMapper.INSTANCE.fromDto(dto);
        employeeService.updateOneKEmployee(employee);
    }

    @Override
    @GetMapping("/users/expiredPhotos")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getExpiredAvatars() {
        return EmployeeMapper.INSTANCE.toListReadDto(employeeService.findExpiredAvatars());
    }

    @Override
    @GetMapping("/users/soonExpiredPhotos")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeReadDto> getSoonExpiredAvatars() {
        return EmployeeMapper.INSTANCE.toListReadDto(employeeService.findWhereAvatarWillExpireSoon());
    }

    @Override
    @PatchMapping("/users/sendEmailsByExpiredPhotos")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> sendEmailsByExpiredPhotos() {
        return employeeService.sendEmailsWhereExpiredPhotos();
    }

    @Override
    @PatchMapping("/users/sendEmailsBySoonExpiredPhotos")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> sendEmailsBySoonExpiredPhotos() {
        return employeeService.sendEmailsWhereAvatarWillExpireSoon();
    }

    @Override
    @PostMapping(
            value = "/users/{id}/avatar",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeReadDto addAvatarByEmployeeId(@PathVariable("id") Integer id,
                                                 @RequestPart("avatar") MultipartFile avatarImg) throws IOException {
        return EmployeeMapper.INSTANCE.toReadDto(employeeService.saveAvatarToEmployee(id, avatarImg));
    }

    @Override
    @PatchMapping("/users/{id}/avatar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAvatarByEmployeeId(@PathVariable("id") Integer id) {
        employeeService.removeEmployeesAvatar(id);
    }

    @Override
    @GetMapping(
            value = "/users/{id}/avatar",
            produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] readAvatarByEmployeeId(@PathVariable("id") Integer id) throws Exception {
        return employeeService.findEmployeesAvatar(id);
    }

    @Override
    @PostMapping("/users/{id}/passes")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeReadDto addWorkPassToEmployee(@PathVariable Integer id,
                                                 @RequestParam(name = "deleteStatus", defaultValue = "EXCHANGED") PassStatus passDeleteStatus) {
        return EmployeeMapper.INSTANCE.toReadDto(employeeService.addWorkPassToEmployee(id, passDeleteStatus));
    }

    @Override
    @PostMapping("/users/{id}/passes/form")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeReadDto addWorkPassToEmployee(@PathVariable Integer id,
                                                 @RequestBody WorkPassRequest request,
                                                 @RequestParam(name = "deleteStatus", defaultValue = "EXCHANGED") PassStatus passDeleteStatus) {
        var pass = WorkPassMapper.INSTANCE.fromRequest(request);
        return EmployeeMapper.INSTANCE.toReadDto(employeeService.addWorkPassToEmployee(id, pass, passDeleteStatus));
    }

    @Override
    @DeleteMapping("/users/{id}/passes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePassFromEmployee(@PathVariable Integer id,
                                       @RequestParam(name = "deleteStatus", defaultValue = "EXPIRED") PassStatus passDeleteStatus) {
        employeeService.deletePassFromEmployee(id, passDeleteStatus);
    }

    @Override
    @GetMapping("/users/{id}/passes/old")
    @ResponseStatus(HttpStatus.OK)
    public List<WorkPassResponse> getOldPassesOfEmployee(@PathVariable Integer id) {
        return WorkPassMapper.INSTANCE.toResponseList(employeeService.getOldPassesOfEmployee(id));
    }

    @PostMapping("/users/{e_id}/cabinets/{c_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeReadDto addEmployeeToCabinet(@PathVariable("e_id") Integer employeeId,
                                                @PathVariable("c_id") Integer cabinetId) {
        return EmployeeMapper.INSTANCE.toReadDto(employeeService.addEmployeeToCabinet(employeeId, cabinetId));
    }

    @DeleteMapping("/users/{e_id}/cabinets/{c_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployeeFromCabinet(@PathVariable("e_id") Integer employeeId,
                                          @PathVariable("c_id") Integer cabinetId) {
        employeeService.removeEmployeeFromCabinet(employeeId, cabinetId);
    }

}
