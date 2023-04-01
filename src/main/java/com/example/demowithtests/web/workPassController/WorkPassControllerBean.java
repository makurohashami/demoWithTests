package com.example.demowithtests.web.workPassController;

import com.example.demowithtests.dto.workPass.WorkPassRequest;
import com.example.demowithtests.dto.workPass.WorkPassResponse;
import com.example.demowithtests.service.workPassService.WorkPassService;
import com.example.demowithtests.util.mappers.WorkPassMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class WorkPassControllerBean implements WorkPassDocumented {

    private final WorkPassService passService;

    @Override
    @PostMapping("/passes")
    @ResponseStatus(HttpStatus.CREATED)
    public WorkPassResponse createPass(@RequestBody WorkPassRequest request) {
        var toResponse = passService.addPass(WorkPassMapper.INSTANCE.fromRequest(request));
        return WorkPassMapper.INSTANCE.toResponse(toResponse);
    }

    @Override
    @GetMapping("/passes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkPassResponse readPass(@PathVariable Integer id) {
        return WorkPassMapper.INSTANCE.toResponse(passService.getPass(id));
    }

    @Override
    @PutMapping("/passes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkPassResponse updatePass(@PathVariable Integer id, @RequestBody WorkPassRequest request) {
        var toResponse = passService.updatePass(id, WorkPassMapper.INSTANCE.fromRequest(request));
        return WorkPassMapper.INSTANCE.toResponse(toResponse);
    }

    @Override
    @PatchMapping("/passes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePass(@PathVariable Integer id) {
        passService.removePass(id);
    }

    @Override
    @GetMapping("/passes/expired")
    @ResponseStatus(HttpStatus.OK)
    public List<WorkPassResponse> getAllExpiredWorkPasses() {
        return WorkPassMapper.INSTANCE.toResponseList(passService.getAllExpiredWorkPasses());
    }
}
