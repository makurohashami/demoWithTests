package com.example.demowithtests.dto.photo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class PhotoRequestDto {

    @Schema(description = "Description of the photo.", required = true)
    public String description;

    @Schema(description = "Camera type of the photo.", required = true)
    public String cameraType;

    @Schema(description = "Url of the photo.", example = "https://google.com", required = true)
    public String photoUrl;

    @JsonIgnore
    public LocalDateTime addDate = LocalDateTime.now();

    @JsonIgnore
    public Boolean isExpired = Boolean.FALSE;
}
