package com.example.demowithtests.dto.photo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class PhotoDto {
    public LocalDateTime addDate = LocalDateTime.now();
    public String description;
    public String cameraType;
    public String photoUrl;
    @JsonIgnore
    public Boolean isExpired = Boolean.FALSE;
}
