package com.example.demowithtests.dto.photo;

import java.time.LocalDateTime;

public class PhotoDto {
    public LocalDateTime addDate = LocalDateTime.now();
    public String description;
    public String cameraType;
    public String photoUrl;
}
