package com.example.demowithtests.web.cabinetController;

import com.example.demowithtests.domain.Cabinet;

public interface CabinetController {

    Cabinet createCabinet(Cabinet cabinet);

    Cabinet readCabinet(Integer id);

    Cabinet updateCabinet(Integer id, Cabinet cabinet);

    void deleteCabinet(Integer id);
}
