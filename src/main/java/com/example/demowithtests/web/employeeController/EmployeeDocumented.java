package com.example.demowithtests.web.employeeController;

import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.domain.PassStatus;
import com.example.demowithtests.dto.employee.EmployeeDto;
import com.example.demowithtests.dto.employee.EmployeeIsVisibleDto;
import com.example.demowithtests.dto.employee.EmployeeReadDto;
import com.example.demowithtests.dto.employee.EmployeeUpdateDto;
import com.example.demowithtests.dto.workPass.WorkPassRequest;
import com.example.demowithtests.dto.workPass.WorkPassResponse;
import com.example.demowithtests.util.exception.ErrorDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Tag(name = "Employee", description = "Employee API")
public interface EmployeeDocumented extends EmployeeController {

    @Override
    @Operation(summary = "This is endpoint to add a new employee.", description = "Create request to add a new employee.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new employee is successfully created and added to database."),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    EmployeeDto saveEmployee(EmployeeDto requestForSave);

    @Override
    @Operation(summary = "This is endpoint to view all employees.", description = "Create request to view all employees.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK.")})
    List<EmployeeReadDto> getAllUsers();

    @Override
    @Operation(summary = "This is endpoint to view page of employees.", description = "Create request to view page of employees.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK.")})
    Page<EmployeeReadDto> getPage(int page, int size);

    @Override
    @Operation(summary = "This is endpoint returned a employee by his id.", description = "Create request to read a employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    EmployeeReadDto getEmployeeById(Integer id);

    @Override
    @Operation(summary = "This is endpoint to update an employee by his id.", description = "Create request to update an employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    EmployeeDto refreshEmployee(Integer id, EmployeeUpdateDto dto);

    @Override
    @Operation(summary = "This is endpoint to remove an employee by his id.", description = "Create request to remove an employee by id", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK. Employee deleted."),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified employee request not found.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    void removeEmployeeById(Integer id);

    @Override
    @Operation(summary = "This is endpoint to remove all employees.", description = "Create request to remove all employees", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK. Employees deleted.")})
    void removeAllUsers();

    @Override
    @Operation(summary = "This is endpoint return page employee by country.", description = "Create request to return page employee by country", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    Page<EmployeeReadDto> findByCountry(String country, int page, int size, List<String> sortList, Sort.Direction sortOrder);

    @Override
    @Operation(summary = "This is endpoint to view all countries of employees.", description = "Create request to view all countries of employees", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK.")})
    List<String> getAllUsersC();

    @Override
    @Operation(summary = "This is endpoint to sorted view all countries of employees.", description = "Create request to sorted view all countries of employees", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK.")})
    List<String> getAllUsersSort();

    @Override
    @Operation(summary = "This is endpoint to find employees email ends with \".com\".", description = "Create request to find employees email ends with \".com\".", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK.")})
    Optional<String> getAllUsersSo();

    @Override
    @Operation(summary = "This is endpoint return list employees by country and gender.", description = "Create request to list employees by country and gender.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    List<EmployeeReadDto> readByGender(Gender gender, String country);

    @Override
    @Operation(summary = "This is endpoint return page employees where specific country is active.", description = "Create request to page employees where specific country is active.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    Page<EmployeeReadDto> readActiveAddressesByCountry(String country, int page, int size);

    @Override
    @Operation(summary = "This is endpoint to get not visible employees.", description = "Create request to get not visible employees.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK.")})
    List<EmployeeIsVisibleDto> getWhereIsVisibleIsNull();

    @Override
    @Operation(summary = "This is endpoint to add 1000 new employees.", description = "Create request to add 1000 new employees.", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED.")})
    void createOneThousandEmployees();

    @Override
    @Operation(summary = "This is endpoint to update last 1000 employees with Http Patch method", description = "Create request to update last 1000 employees", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    void updateEmployeePatch(EmployeeDto dto);

    @Override
    @Operation(summary = "This is endpoint to update last 1000 employees with Http Put method", description = "Create request to update last 1000 employees", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    void updateEmployeePut(EmployeeDto dto);

    @Override
    @Operation(summary = "This is endpoint to return employees where expired photos", description = "Create request to return employees where expired photos", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK.")})
    List<EmployeeReadDto> getExpiredAvatars();

    @Override
    @Operation(summary = "This is endpoint sends emails to employees where expired photos", description = "Send emails to employees where expired photos", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK.")})
    Set<String> sendEmailsByExpiredPhotos();

    @Override
    @Operation(summary = "This is endpoint to add/update avatar for employee", description = "Create request to add/update avatar for employee", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    EmployeeReadDto addAvatarByEmployeeId(Integer id, MultipartFile avatarImg) throws IOException;

    @Override
    @Operation(summary = "This is endpoint to remove avatar of employee.", description = "Create request to remove avatar of employee", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content.")})
    void deleteAvatarByEmployeeId(Integer id);

    @Override
    @Operation(summary = "This is endpoint return avatar of employee. Return JPEG file", description = "Create request to return avatar of employee", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK.")})
    byte[] readAvatarByEmployeeId(Integer id) throws Exception;

    @Override
    @Operation(summary = "This is endpoint to return employees where photos will expire soon", description = "Create request to return employees where photos will expire soon", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK.")})
    List<EmployeeReadDto> getSoonExpiredAvatars();

    @Override
    @Operation(summary = "This is endpoint sends emails to employees where photos will expire soon", description = "Send emails to employees where photos will expire soon", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK.")})
    Set<String> sendEmailsBySoonExpiredPhotos();

    @Override
    @Operation(summary = "This is endpoint to add/update work pass for employee", description = "Create request to add/update work pass for employee", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED."),
            @ApiResponse(responseCode = "404", description = "Not found.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    EmployeeReadDto addWorkPassToEmployee(Integer id, PassStatus passDeleteStatus);

    @Override
    @Operation(summary = "This is endpoint to add/update work pass for employee", description = "Create request to add/update work pass for employee", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED."),
            @ApiResponse(responseCode = "404", description = "Not found.", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    EmployeeReadDto addWorkPassToEmployee(Integer id, WorkPassRequest request, PassStatus passDeleteStatus);

    @Override
    @Operation(summary = "This is endpoint to remove work pass for of employee.", description = "Create request to remove work pass for of employee", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content."),
            @ApiResponse(responseCode = "404", description = "Not found.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    void deletePassFromEmployee(Integer id, PassStatus passDeleteStatus);

    @Override
    @Operation(summary = "This is endpoint to get old work passes of employee.", description = "Create request to get old work passes of employee", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK."),
            @ApiResponse(responseCode = "404", description = "Not found.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    List<WorkPassResponse> getOldPassesOfEmployee(Integer id);

    @Override
    @Operation(summary = "This is endpoint to add cabinet for employee", description = "Create request to add cabinet for employee", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED."),
            @ApiResponse(responseCode = "404", description = "Not found.", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    EmployeeReadDto addEmployeeToCabinet(Integer employeeId, Integer cabinetId);

    @Override
    @Operation(summary = "This is endpoint to remove cabinet for of employee.", description = "Create request to remove cabinet for of employee", tags = {"Employee"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content."),
            @ApiResponse(responseCode = "404", description = "Not found.", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    void removeEmployeeFromCabinet(Integer employeeId, Integer cabinetId);
}
