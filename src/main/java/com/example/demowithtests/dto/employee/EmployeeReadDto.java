package com.example.demowithtests.dto.employee;

import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.dto.address.AddressResponseDto;
import com.example.demowithtests.dto.cabinet.CabinetResponse;
import com.example.demowithtests.dto.workPass.WorkPassResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

public class EmployeeReadDto {

    @Schema(description = "Name of an employee.", example = "Billy")
    public String name;

    @Schema(description = "Name of the country.", example = "EN")
    public String country;

    @Schema(description = "Email address of an employee.", example = "billys@mail.com")
    public String email;

    @Schema(description = "Gender of an employee.")
    public Gender gender;
    @Schema(description = "Addresses of an employee.")
    public Set<AddressResponseDto> addresses;

    @Schema(description = "Link to avatar of an employee.")
    public String avatarUrl;

    @Schema(description = "Work pass of an employee.")
    public WorkPassResponse workPass;

    @Schema(description = "Cabinets of an employee.")
    public Set<CabinetResponse> cabinets;
}
