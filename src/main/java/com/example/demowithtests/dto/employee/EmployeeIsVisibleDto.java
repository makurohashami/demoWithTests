package com.example.demowithtests.dto.employee;

import io.swagger.v3.oas.annotations.media.Schema;

public class EmployeeIsVisibleDto {

    public Integer id;
    public Boolean isVisible;

    @Schema(description = "Email address of an employee.", example = "billys@mail.com", required = true)
    public String email;

}
