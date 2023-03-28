package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.WorkPass;
import com.example.demowithtests.dto.workPass.WorkPassRequest;
import com.example.demowithtests.dto.workPass.WorkPassResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WorkPassMapper {

    WorkPassMapper INSTANCE = Mappers.getMapper(WorkPassMapper.class);

    WorkPass fromRequest(WorkPassRequest request);

    WorkPassResponse toResponse(WorkPass workPass);
}
