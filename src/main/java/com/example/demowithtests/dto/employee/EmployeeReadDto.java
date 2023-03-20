package com.example.demowithtests.dto.employee;

import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.dto.address.AddressRequestDto;
import com.example.demowithtests.dto.address.AddressResponseDto;
import com.example.demowithtests.dto.photo.PhotoRequestDto;
import com.example.demowithtests.dto.photo.PhotoResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

public class EmployeeReadDto {

    @Schema(description = "Name of an employee.", example = "Billy", required = true)
    public String name;

    @Schema(description = "Name of the country.", example = "EN", required = true)
    public String country;

    @Schema(description = "Email address of an employee.", example = "billys@mail.com", required = true)
    public String email;

    @Schema(description = "Gender of an employee.", required = true)
    public Gender gender;
    @Schema(description = "Addresses of an employee.", required = true)
    public Set<AddressResponseDto> addresses;

    @Schema(description = "Photos of an employee.", required = true)
    public Set<PhotoResponseDto> photos;


}
