package com.example.demowithtests.dto.employee;

import io.swagger.v3.oas.annotations.media.Schema;

public class EmployeeIsVisibleDto {

    @Schema(description = "Id of an employee", example = "1", required = true)
    public Integer id;
    @Schema(description = "Employee is visible or not", example = "false", required = true)
    public Boolean isVisible;

    @Schema(description = "Email address of an employee.", example = "billys@mail.com", required = true)
    public String email;

}
