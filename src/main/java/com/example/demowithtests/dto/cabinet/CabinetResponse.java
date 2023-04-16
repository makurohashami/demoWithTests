package com.example.demowithtests.dto.cabinet;

import io.swagger.v3.oas.annotations.media.Schema;

public class CabinetResponse {

    @Schema(description = "Id of a cabinet.", example = "1")
    public Integer id;
    @Schema(description = "Name of a cabinet.", example = "Oslo")
    public String name;
    @Schema(description = "Capacity of a cabinet.", example = "5")
    public Integer capacity;
    @Schema(description = "Count of a free places in cabinet.", example = "3")
    public Integer freePlacesCount;
}
