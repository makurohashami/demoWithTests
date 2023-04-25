package com.example.demowithtests.webTests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.employee.EmployeeDto;
import com.example.demowithtests.dto.employee.EmployeeReadDto;
import com.example.demowithtests.dto.employee.EmployeeUpdateDto;
import com.example.demowithtests.service.employeeService.EmployeeService;
import com.example.demowithtests.util.mappers.EmployeeMapper;
import com.example.demowithtests.web.employeeController.EmployeeControllerBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = EmployeeControllerBean.class)
@DisplayName("Employee Controller Tests")
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private EmployeeService service;

    @MockBean
    private EmployeeMapper employeeMapper;

    @Test
    @DisplayName("POST /api/users")
    @WithMockUser(roles = "ADMIN")
    public void createPassTest() throws Exception {
        var response = new EmployeeDto();
        response.id = 1;
        var employee = Employee.builder().id(1).name("Mike").email("mail@mail.com").build();

        when(employeeMapper.toDto(any(Employee.class))).thenReturn(response);
        when(employeeMapper.fromDto(any(EmployeeDto.class))).thenReturn(employee);
        when(service.create(any(Employee.class))).thenReturn(employee);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));

        verify(service).create(any());
    }

    @Test
    @DisplayName("GET /api/users/{id}")
    @WithMockUser(roles = "USER")
    public void getPassByIdTest() throws Exception {
        var response = new EmployeeReadDto();
        var employee = Employee.builder()
                .id(1)
                .name("Mike")
                .build();

        when(employeeMapper.toReadDto(any(Employee.class))).thenReturn(response);
        when(service.getById(1)).thenReturn(employee);

        MockHttpServletRequestBuilder mockRequest = get("/api/users/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Mike")));

        verify(service).getById(anyInt());
    }

    @Test
    @DisplayName("PUT /api/users/{id}")
    @WithMockUser(roles = "ADMIN")
    public void updatePassByIdTest() throws Exception {
        var response = new EmployeeDto();
        response.id = 1;
        var employee = Employee.builder().id(1).build();

        when(employeeMapper.toDto(any(Employee.class))).thenReturn(response);
        when(employeeMapper.fromUpdateDto(any(EmployeeUpdateDto.class))).thenReturn(employee);
        when(service.updateById(eq(1), any(Employee.class))).thenReturn(employee);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(service).updateById(eq(1), any(Employee.class));
    }

    @Test
    @DisplayName("PATCH /api/users/{id}")
    @WithMockUser(roles = "ADMIN")
    public void deletePassTest() throws Exception {
        doNothing().when(service).removeById(1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .patch("/api/users/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());

        verify(service).removeById(1);
    }

    @Test
    @DisplayName("POST /api/users/{id}/avatar")
    @WithMockUser(roles = "ADMIN")
    public void addAvatarTest() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "avatar",
                "avatar.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Some image".getBytes());
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("John");
        EmployeeReadDto employeeReadDto = new EmployeeReadDto();
        employeeReadDto.name = "John";

        when(service.saveAvatarToEmployee(eq(1), any(MultipartFile.class))).thenReturn(employee);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .multipart("/api/users/1/avatar")
                .file(multipartFile);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(employeeReadDto.name)));

        verify(service, times(1)).saveAvatarToEmployee(eq(1), any(MultipartFile.class));
    }

    @Test
    @DisplayName("GET /api/users/{id}/avatar")
    @WithMockUser(roles = "USER")
    public void getAvatarTest() throws Exception {
        byte[] avatarImage = "Some image".getBytes();

        when(service.findEmployeesAvatar(eq(1))).thenReturn(avatarImage);

        MvcResult result = mockMvc.perform(get("/api/users/1/avatar"))
                .andExpect(status().isOk())
                .andReturn();

        verify(service, times(1)).findEmployeesAvatar(eq(1));
        String contentType = result.getResponse().getContentType();
        assertNotNull(contentType);
        assertTrue(contentType.contains(MediaType.IMAGE_JPEG_VALUE));
        byte[] responseBytes = result.getResponse().getContentAsByteArray();
        assertArrayEquals(avatarImage, responseBytes);
    }

    @Test
    @DisplayName("GET /api/users/p")
    @WithMockUser(roles = "USER")
    public void getUsersPageTest() throws Exception {
        var employee1 = Employee.builder().id(1).name("John").country("US").build();
        var employee2 = Employee.builder().id(2).name("Jane").country("UK").build();
        var employee3 = Employee.builder().id(3).name("Bob").country("US").build();
        List<Employee> list = Arrays.asList(employee1, employee2, employee3);
        Page<Employee> employeesPage = new PageImpl<>(list);
        Pageable pageable = PageRequest.of(0, 5);

        when(service.getAllWithPagination(eq(pageable))).thenReturn(employeesPage);

        MvcResult result = mockMvc.perform(get("/api/users/p")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andReturn();

        verify(service).getAllWithPagination(eq(pageable));

        String contentType = result.getResponse().getContentType();
        assertNotNull(contentType);
        assertTrue(contentType.contains(MediaType.APPLICATION_JSON_VALUE));
        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent);
    }
}
