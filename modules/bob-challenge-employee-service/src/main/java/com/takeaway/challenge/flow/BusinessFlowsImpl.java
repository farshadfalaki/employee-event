package com.takeaway.challenge.flow;

import com.takeaway.challenge.constants.EventType;
import com.takeaway.challenge.dto.EmployeeRequestDto;
import com.takeaway.challenge.dto.EmployeeResponseDto;
import com.takeaway.challenge.mapper.EmployeeMapper;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.service.EmployeeService;
import com.takeaway.challenge.service.OutboxService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@AllArgsConstructor
public class BusinessFlowsImpl implements BusinessFlows {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;
    private final OutboxService outboxService;

    @Override
    @Transactional
    public EmployeeResponseDto createEmployeeAndPublish(EmployeeRequestDto employeeRequestDto) {
        Employee employee = employeeMapper.toModel(employeeRequestDto);
        employee = employeeService.create(employee);
        EmployeeResponseDto employeeResponseDto = employeeMapper.toDto(employee);
        outboxService.create(employeeResponseDto.getId(),EventType.CREATE_EVENT);
        return employeeResponseDto;
    }

    @Override
    @Transactional
    public EmployeeResponseDto updateEmployeeAndPublish(EmployeeRequestDto employeeRequestDto,String employeeId) {
        Employee employee = employeeMapper.toModel(employeeRequestDto);
        employee.setId(employeeId);
        employee = employeeService.update(employee);
        EmployeeResponseDto employeeResponseDto = employeeMapper.toDto(employee);
        outboxService.create(employeeResponseDto.getId(),EventType.UPDATE_EVENT);
        return employeeResponseDto;
    }

    @Override
    @Transactional
    public void deleteEmployeeAndPublish(String employeeId) {
        employeeService.remove(employeeId);
        outboxService.create(employeeId,EventType.DELETE_EVENT);
    }
}
