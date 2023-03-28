package com.example.demowithtests.service.workPassService;

import com.example.demowithtests.domain.WorkPass;

import java.util.List;

public interface WorkPassService {

    WorkPass addPass(WorkPass pass);

    WorkPass getPass(Integer id);

    WorkPass updatePass(Integer id, WorkPass pass);

    void removePass(Integer id);

    WorkPass getAvailablePass(Integer id);

    List<WorkPass> getAllExpiredWorkPasses();
}
