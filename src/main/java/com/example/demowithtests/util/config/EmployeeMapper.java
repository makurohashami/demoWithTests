package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.Avatar;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.employee.EmployeeDto;
import com.example.demowithtests.dto.employee.EmployeeIsVisibleDto;
import com.example.demowithtests.dto.employee.EmployeeReadDto;
import com.example.demowithtests.dto.employee.EmployeeUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee fromDto(EmployeeDto dto);

    EmployeeDto toDto(Employee employee);

    @Mapping(source = "avatars", target = "avatarUrl", qualifiedByName = "getOnlyAvatarUrl")
    EmployeeReadDto toReadDto(Employee employee);

    List<EmployeeIsVisibleDto> toListIsVisibleDto(List<Employee> employees);

    List<EmployeeReadDto> toListReadDto(List<Employee> employees);

    Employee fromUpdateDto(EmployeeUpdateDto dto);

    @Named("getOnlyAvatarUrl")
    static String getOnlyAvatarUrl(Set<Avatar> avatars) {
        var list = avatars
                .stream()
                .filter(avatar -> avatar.getIsExpired().equals(Boolean.FALSE))
                .collect(Collectors.toList());
        return list.isEmpty() ? null : list.get(0).getImgUrl();
    }
}
