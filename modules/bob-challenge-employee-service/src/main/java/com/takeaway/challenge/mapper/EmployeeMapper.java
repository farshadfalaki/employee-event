package com.takeaway.challenge.mapper;

import com.takeaway.challenge.dto.EmployeeRequestDto;
import com.takeaway.challenge.dto.EmployeeResponseDto;
import com.takeaway.challenge.model.Employee;

public interface EmployeeMapper {
    Employee toModel(EmployeeRequestDto employeeDto);
    EmployeeResponseDto toDto(Employee employee);
}
