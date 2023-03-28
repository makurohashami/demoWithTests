package com.example.demowithtests.service.workPassService;

import com.example.demowithtests.domain.WorkPass;
import com.example.demowithtests.repository.WorkPassRepository;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import com.example.demowithtests.util.exception.ResourceUnavailableException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WorkPassServiceBean implements WorkPassService {

    private final WorkPassRepository passRepository;

    @Override
    public WorkPass addPass(WorkPass pass) {
        return passRepository.save(pass);
    }

    @Override
    public WorkPass getPass(Integer id) {
        var pass = passRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        if (pass.getIsDeleted())
            throw new ResourceUnavailableException();
        return pass;
    }

    @Override
    public WorkPass updatePass(Integer id, WorkPass pass) {
        return passRepository.findById(id)
                .map(workpass -> {
                    workpass.setAccessLevel(pass.getAccessLevel());
                    return passRepository.save(workpass);
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void removePass(Integer id) {
        var pass = passRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        pass.setIsDeleted(Boolean.TRUE);
        passRepository.save(pass);
    }

    @Override
    public WorkPass getAvailablePass(Integer id) {
        var pass = passRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        if (pass.getIsDeleted() || pass.getEmployee() != null)
            throw new ResourceUnavailableException();
        return pass;
    }
}