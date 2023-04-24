package com.example.demowithtests.webTests;

import com.example.demowithtests.domain.AccessLevel;
import com.example.demowithtests.domain.PassStatus;
import com.example.demowithtests.domain.WorkPass;
import com.example.demowithtests.dto.workPass.WorkPassRequest;
import com.example.demowithtests.dto.workPass.WorkPassResponse;
import com.example.demowithtests.service.workPassService.WorkPassService;
import com.example.demowithtests.util.mappers.WorkPassMapper;
import com.example.demowithtests.web.workPassController.WorkPassControllerBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = WorkPassControllerBean.class)
@DisplayName("WorkPass Controller Tests")
public class WorkPassControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private WorkPassService service;

    @MockBean
    private WorkPassMapper passMapper;

    @Test
    @DisplayName("POST /api/passes")
    @WithMockUser(roles = "ADMIN")
    public void createPassTest() throws Exception {
        var response = new WorkPassResponse();
        response.id = 1;
        var pass = WorkPass.builder().id(1).build();

        when(passMapper.toResponse(any(WorkPass.class))).thenReturn(response);
        when(passMapper.fromRequest(any(WorkPassRequest.class))).thenReturn(pass);
        when(service.addPass(any(WorkPass.class))).thenReturn(pass);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/passes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(pass));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));

        verify(service).addPass(any());
    }

    @Test
    @DisplayName("GET /api/passes/{id}")
    @WithMockUser(roles = "USER")
    public void getPassByIdTest() throws Exception {
        var response = new WorkPassResponse();
        response.id = 1;
        var pass = WorkPass.builder().id(1).build();

        when(passMapper.toResponse(any(WorkPass.class))).thenReturn(response);
        when(service.getPass(1)).thenReturn(pass);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/passes/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(service).getPass(anyInt());
    }

    @Test
    @DisplayName("PUT /api/passes/{id}")
    @WithMockUser(roles = "ADMIN")
    public void updatePassByIdTest() throws Exception {
        var response = new WorkPassResponse();
        response.id = 1;
        response.accessLevel = AccessLevel.B2;
        var pass = WorkPass.builder().id(1).build();

        when(passMapper.toResponse(any(WorkPass.class))).thenReturn(response);
        when(passMapper.fromRequest(any(WorkPassRequest.class))).thenReturn(pass);
        when(service.updatePass(eq(1), any(WorkPass.class))).thenReturn(pass);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/passes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(pass));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(service).updatePass(eq(1), any(WorkPass.class));
    }

    @Test
    @DisplayName("PATCH /api/passes/{id}")
    @WithMockUser(roles = "ADMIN")
    public void deletePassTest() throws Exception {
        doNothing().when(service).removePass(1, PassStatus.EXPIRED);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .patch("/api/passes/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());

        verify(service).removePass(1, PassStatus.EXPIRED);
    }

    @Test
    @DisplayName("GET /api/passes/expired")
    @WithMockUser(roles = "USER")
    public void getAllExpiredPassesTest() throws Exception {
        var pass = new WorkPass();
        pass.setId(1);
        List<WorkPass> list = new ArrayList<>(List.of(pass));
        var response = new WorkPassResponse();
        response.id = 1;
        List<WorkPassResponse> listResponse = new ArrayList<>(List.of(response));

        when(service.getAllExpiredWorkPasses()).thenReturn(list);
        when(passMapper.toResponseList(any())).thenReturn(listResponse);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/passes/expired");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(service).getAllExpiredWorkPasses();
    }
}
