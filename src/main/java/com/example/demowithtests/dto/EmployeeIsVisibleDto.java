package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;

public class EmployeeIsVisibleDto {

    public Integer id;
    public Boolean isVisible;

    @Schema(description = "Name of an employee.", example = "Billy", required = true)
    public String name;

    @Schema(description = "Name of the country.", example = "England", required = true)
    public String country;

    @Schema(description = "Email address of an employee.", example = "billys@mail.com", required = true)
    public String email;

    public Gender gender;

}
