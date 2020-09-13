package com.takeaway.challenge.flow;

import com.takeaway.challenge.dto.EmployeeRequestDto;
import com.takeaway.challenge.dto.EmployeeResponseDto;

public interface BusinessFlows {
    EmployeeResponseDto createEmployeeAndPublish(EmployeeRequestDto employeeRequestDto);

    EmployeeResponseDto updateEmployeeAndPublish(EmployeeRequestDto employeeRequestDto, String employeeId);

    void deleteEmployeeAndPublish(String employeeId);
}
