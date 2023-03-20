package com.example.demowithtests.dto.address;

import io.swagger.v3.oas.annotations.media.Schema;

public class AddressResponseDto {

    @Schema(description = "Name of the country.", example = "England")
    public String country;

    @Schema(description = "Name of the city.", example = "London")
    public String city;

    @Schema(description = "Name of the street.", example = "Downing Street")
    public String street;
}
