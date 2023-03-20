package com.example.demowithtests.dto.photo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class PhotoResponseDto {

    @Schema(description = "Description of the photo.", required = true)
    public String description;

    @Schema(description = "Camera type of the photo.", required = true)
    public String cameraType;

    @Schema(description = "Url of the photo.", example = "https://google.com", required = true)
    public String photoUrl;

    @Schema(description = "Add date of the photo.", example = "2023-03-15 12:36", required = true)
    public LocalDateTime addDate;
}
