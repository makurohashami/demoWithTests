package com.example.demowithtests.service.cabinetService;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.repository.CabinetRepository;
import com.example.demowithtests.util.exception.ResourceNotFoundException;
import com.example.demowithtests.util.exception.ResourceUnavailableException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CabinetServiceBean implements CabinetService {

    private final CabinetRepository cabinetRepository;

    @Override
    public Cabinet addCabinet(Cabinet cabinet) {
        return cabinetRepository.save(cabinet);
    }

    @Override
    public Cabinet getCabinet(Integer id) {
        var cabinet = cabinetRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        if (cabinet.getIsDeleted())
            throw new ResourceUnavailableException();
        return cabinet;
    }

    @Override
    public Cabinet updateCabinet(Integer id, Cabinet cabinet) {
        return cabinetRepository.findById(id)
                .map(c -> {
                    c.setName(cabinet.getName());
                    return cabinetRepository.save(c);
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void removeCabinet(Integer id) {
        var cabinet = cabinetRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        cabinet.setIsDeleted(Boolean.TRUE);
        cabinetRepository.save(cabinet);
    }
}
