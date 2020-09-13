package com.takeaway.challenge.mapper;

import com.takeaway.challenge.dto.DepartmentDto;
import com.takeaway.challenge.model.Department;

public interface DepartmentMapper {
    Department toModel(DepartmentDto departmentDto);
    DepartmentDto toDto(Department department);
}
