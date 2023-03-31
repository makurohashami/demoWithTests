package com.example.demowithtests.dto.workPass;

import com.example.demowithtests.domain.AccessLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public class WorkPassRequest {

    @Schema(description = "Work pass access level.", example = "A2")
    public AccessLevel accessLevel = AccessLevel.A1;

    @JsonIgnore
    public String uuid = UUID.randomUUID().toString();
    @JsonIgnore
    public Boolean isDeleted = Boolean.FALSE;
    @JsonIgnore
    public Boolean isFree = Boolean.TRUE;
}
