package com.example.demowithtests.dto.employee;

import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.dto.address.AddressRequestDto;
import com.example.demowithtests.util.annotations.BlockedEmailDomains;
import com.example.demowithtests.util.annotations.CountryRightFormed;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class EmployeeDto {

    @Schema(description = "Id of an employee. Do NOT put to request", example = "1")
    public Integer id;

    @NotNull(message = "Name may not be null")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @Schema(description = "Name of an employee.", example = "Billy", required = true)
    public String name;

    @Schema(description = "Name of the country.", example = "EN")
    @CountryRightFormed
    public String country;

    @Email
    @NotNull
    @Schema(description = "Email address of an employee.", example = "billys@mail.com", required = true)
    @BlockedEmailDomains(contains = {".com1", ".ru", ".su"})
    public String email;

    @Schema(description = "Addresses of an employee.")
    public Set<AddressRequestDto> addresses = new HashSet<>();

    @Schema(description = "Gender of an employee.")
    public Gender gender;

    @JsonIgnore
    public Boolean isVisible = Boolean.TRUE;
}
