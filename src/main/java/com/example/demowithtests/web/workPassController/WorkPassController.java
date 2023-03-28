package com.example.demowithtests.web.workPassController;

import com.example.demowithtests.dto.workPass.WorkPassRequest;
import com.example.demowithtests.dto.workPass.WorkPassResponse;

import java.util.List;

public interface WorkPassController {
    WorkPassResponse createPass(WorkPassRequest request);

    WorkPassResponse readPass(Integer id);

    WorkPassResponse updatePass(Integer id, WorkPassRequest request);

    void deletePass(Integer id);

    List<WorkPassResponse> getAllExpiredWorkPasses();
}
