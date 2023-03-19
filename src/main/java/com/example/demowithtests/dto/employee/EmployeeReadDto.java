package com.example.demowithtests.dto.employee;

import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.dto.address.AddressDto;
import com.example.demowithtests.dto.photo.PhotoDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

public class EmployeeReadDto {

    @Schema(description = "Name of an employee.", example = "Billy", required = true)
    public String name;

    public String country;

    public String email;

    public Set<AddressDto> addresses;

    public Set<PhotoDto> photos;

    public Gender gender;

}
