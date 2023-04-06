package com.example.demowithtests.util.mappers;

import com.example.demowithtests.domain.EmployeesCabinets;
import com.example.demowithtests.dto.cabinet.CabinetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EmployeesCabinetsMapper {

    @Mapping(source = "cabinet", target = ".")
    CabinetResponse toResponse(EmployeesCabinets employeesCabinets);

}
