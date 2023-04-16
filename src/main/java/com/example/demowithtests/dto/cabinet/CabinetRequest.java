package com.example.demowithtests.dto.cabinet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CabinetRequest {

    @NotNull
    @Size(min = 3, max = 32, message = "Name must be between 3 and 32 characters")
    @Schema(description = "Name of a cabinet.", example = "Oslo")
    public String name;
    @NotNull
    @Min(value = 1, message = "Min capacity is 1")
    @Max(value = 30, message = "Max capacity is 30")
    @Schema(description = "Capacity of a cabinet.", example = "5")
    public Integer capacity = 1;

    @JsonIgnore
    public Boolean isDeleted = Boolean.FALSE;
    @JsonIgnore
    public Boolean hasFreePlaces = Boolean.TRUE;
}
