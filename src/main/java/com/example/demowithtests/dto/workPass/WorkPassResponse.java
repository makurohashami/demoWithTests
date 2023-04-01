package com.example.demowithtests.dto.workPass;

import com.example.demowithtests.domain.AccessLevel;
import com.example.demowithtests.domain.PassStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class WorkPassResponse {

    @Schema(description = "Id of work pass.", example = "1")
    public Integer id;
    @Schema(description = "Uuid of work pass.", example = "b2716877-4006-4aaa-af57-bb59551ae7c6")
    public String uuid;
    @Schema(description = "Work pass access level.", example = "A2")
    public AccessLevel accessLevel;
    @Schema(description = "Expire date of work pass.", example = "2025-03-28 21:03:42.547678")
    public LocalDateTime expireDate;
    @Schema(description = "Status of work pass.", example = "ACTIVE")
    public PassStatus passStatus;
    @Schema(description = "Id of previous work pass.", example = "1")
    public Integer prevPassId;

    public WorkPassResponse prevPass;

}
