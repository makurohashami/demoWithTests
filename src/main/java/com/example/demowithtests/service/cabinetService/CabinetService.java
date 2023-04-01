package com.example.demowithtests.service.cabinetService;

import com.example.demowithtests.domain.Cabinet;

public interface CabinetService {

    Cabinet addCabinet(Cabinet cabinet);

    Cabinet getCabinet(Integer id);

    Cabinet updateCabinet(Integer id, Cabinet cabinet);

    void removeCabinet(Integer id);
}
