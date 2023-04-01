package com.example.demowithtests.web.cabinetController;

import com.example.demowithtests.dto.cabinet.CabinetRequest;
import com.example.demowithtests.dto.cabinet.CabinetResponse;

public interface CabinetController {

    CabinetResponse createCabinet(CabinetRequest request);

    CabinetResponse readCabinet(Integer id);

    CabinetResponse updateCabinet(Integer id, CabinetRequest request);

    void deleteCabinet(Integer id);
}
