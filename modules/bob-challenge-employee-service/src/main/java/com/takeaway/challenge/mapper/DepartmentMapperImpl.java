package com.takeaway.challenge.mapper;

import com.takeaway.challenge.dto.DepartmentDto;
import com.takeaway.challenge.model.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapperImpl implements DepartmentMapper{
    public Department toModel(DepartmentDto departmentDto){
        if(departmentDto==null)
            return null;
        return new Department(departmentDto.getId(),departmentDto.getName());
    }

    public DepartmentDto toDto(Department department){
        if(department == null)
            return null;
        return new DepartmentDto(department.getId(),department.getName());
    }
}
