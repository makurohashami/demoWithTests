package com.example.demowithtests.util.mappers;

import com.example.demowithtests.domain.WorkPass;
import com.example.demowithtests.dto.workPass.WorkPassRequest;
import com.example.demowithtests.dto.workPass.WorkPassResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface WorkPassMapper {

    WorkPassMapper INSTANCE = Mappers.getMapper(WorkPassMapper.class);

    WorkPass fromRequest(WorkPassRequest request);

    @Mapping(source = "prevPass.id", target = "prevPassId")
    WorkPassResponse toResponse(WorkPass workPass);

    List<WorkPassResponse> toResponseList(List<WorkPass> workPasses);
}
