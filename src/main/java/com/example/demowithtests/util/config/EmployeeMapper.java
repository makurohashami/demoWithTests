package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeIsVisibleDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.dto.EmployeeUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee fromDto(EmployeeDto dto);

    EmployeeDto toDto(Employee employee);

    EmployeeReadDto toReadDto(Employee employee);

    List<EmployeeIsVisibleDto> toListIsVisibleDto(List<Employee> employees);

    List<EmployeeReadDto> toListReadDto(List<Employee> employees);

    Employee fromUpdateDto(EmployeeUpdateDto dto);

    //Page<EmployeeReadDto> toPageReadDto(Page<Employee> employeePage);
}
