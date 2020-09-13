package com.takeaway.challenge.flow;

import com.takeaway.challenge.constants.EventType;
import com.takeaway.challenge.dto.EmployeeRequestDto;
import com.takeaway.challenge.dto.EmployeeResponseDto;
import com.takeaway.challenge.mapper.EmployeeMapper;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.model.Outbox;
import com.takeaway.challenge.service.EmployeeService;
import com.takeaway.challenge.service.OutboxService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BusinessFlowsTest {
    @Mock
    EmployeeService employeeService;
    @Mock
    EmployeeMapper employeeMapper;
    @Mock
    OutboxService outboxService;
    @InjectMocks
    BusinessFlowsImpl businessFlows;

    @Test
    public void createEmployeeAndPublish_withSuccessfulEmployeeCreation_shouldCreateOutbox() throws ParseException {
        //given
        String sampleEmail = "aa1ada@bb.com";
        String sampleFullName = "John smith";
        String sampleBirthdayString = "1979-10-14";
        Department sampleDepartment = new Department(10L,"sample name");
        String sampleOutboxId = "1000";
        String sampleEmployeeId = "123";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleBirthday = simpleDateFormat.parse(sampleBirthdayString);
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto(sampleEmail,sampleFullName,sampleBirthdayString,sampleDepartment.getId());
        Employee employee = Employee.builder().email(sampleEmail)
                .fullName(sampleFullName).depId(sampleDepartment.getId()).birthday(sampleBirthday).build();
        EmployeeResponseDto expectedEmployeeResponseDto = new EmployeeResponseDto(sampleEmployeeId,sampleEmail,sampleFullName,sampleBirthdayString,sampleDepartment);

        when(employeeMapper.toModel(employeeRequestDto)).thenReturn(employee);
        when(employeeService.create(employee)).thenAnswer(invocationOnMock -> {
            Employee input = invocationOnMock.getArgument(0);
            input.setId(sampleEmployeeId);
            return invocationOnMock.getArgument(0);
        });
        when(employeeMapper.toDto(employee)).thenReturn(expectedEmployeeResponseDto);
        when(outboxService.create(expectedEmployeeResponseDto.getId(),EventType.CREATE_EVENT)).thenAnswer(invocationOnMock -> new Outbox(sampleOutboxId,invocationOnMock.getArgument(0),invocationOnMock.getArgument(1), Instant.now(),false));
        //when
        EmployeeResponseDto actualEmployeeResponseDto = businessFlows.createEmployeeAndPublish(employeeRequestDto);
        //then
        assertEquals(expectedEmployeeResponseDto,actualEmployeeResponseDto);
        verify(employeeMapper).toModel(employeeRequestDto);
        verify(employeeService).create(employee);
        verify(employeeMapper).toDto(employee);
        verify(outboxService).create(sampleEmployeeId,EventType.CREATE_EVENT);
    }

    @Test
    public void createEmployeeAndPublish_withFailedCreation_shouldNotCreateOutboxRecord() throws ParseException {
        //given
        Exception expectedException = null;
        String sampleEmployeeId = "123";
        String sampleEmail = "aa1ada@bb.com";
        String sampleFullName = "John smith";
        String sampleBirthdayString = "1979-10-14";
        Department sampleDepartment = new Department(10L,"sample name");
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto(sampleEmail,sampleFullName,sampleBirthdayString,sampleDepartment.getId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleBirthday = simpleDateFormat.parse(sampleBirthdayString);
        Employee employee = Employee.builder().email(sampleEmail)
                .fullName(sampleFullName).depId(sampleDepartment.getId()).birthday(sampleBirthday).build();

        when(employeeMapper.toModel(employeeRequestDto)).thenReturn(employee);
        when(employeeService.create(employee)).thenThrow(new RuntimeException("Any problem while creation"));
        //when
        try {
             businessFlows.createEmployeeAndPublish(employeeRequestDto);
        }catch(Exception e){
            expectedException = e;
        }
        //then
        assertNotNull(expectedException);
        verify(employeeMapper).toModel(employeeRequestDto);
        verify(employeeService).create(employee);
        verify(employeeMapper,times(0)).toDto(any());
        verify(outboxService,times(0)).create(any(),any());
    }

    @Test
    public void updateEmployeeAndPublish_withSuccessfulUpdate_shouldCreateOutbox() throws ParseException {
        //given
        String sampleEmail = "aa1ada@bb.com";
        String sampleFullName = "John smith";
        String sampleBirthdayString = "1979-10-14";
        Department sampleDepartment = new Department(10L,"sample name");
        String sampleOutboxId = "1000";
        String sampleEmployeeId = "123";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleBirthday = simpleDateFormat.parse(sampleBirthdayString);
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto(sampleEmail,sampleFullName,sampleBirthdayString,sampleDepartment.getId());
        Employee employee = Employee.builder().email(sampleEmail)
                .fullName(sampleFullName).depId(sampleDepartment.getId()).birthday(sampleBirthday).build();
        EmployeeResponseDto expectedEmployeeResponseDto = new EmployeeResponseDto(sampleEmployeeId,sampleEmail,sampleFullName,sampleBirthdayString,sampleDepartment);

        when(employeeMapper.toModel(employeeRequestDto)).thenReturn(employee);
        when(employeeService.update(employee)).thenAnswer(invocationOnMock -> {
            Employee input = invocationOnMock.getArgument(0);
            input.setId(sampleEmployeeId);
            return invocationOnMock.getArgument(0);
        });
        when(employeeMapper.toDto(employee)).thenReturn(expectedEmployeeResponseDto);
        when(outboxService.create(expectedEmployeeResponseDto.getId(),EventType.UPDATE_EVENT)).thenAnswer(invocationOnMock -> new Outbox(sampleOutboxId,invocationOnMock.getArgument(0),invocationOnMock.getArgument(1), Instant.now(),false));
        //when
        EmployeeResponseDto actualEmployeeResponseDto = businessFlows.updateEmployeeAndPublish(employeeRequestDto,sampleEmployeeId);
        //then
        assertEquals(expectedEmployeeResponseDto,actualEmployeeResponseDto);
        verify(employeeMapper).toModel(employeeRequestDto);
        verify(employeeService).update(employee);
        verify(employeeMapper).toDto(employee);
        verify(outboxService).create(sampleEmployeeId,EventType.UPDATE_EVENT);
    }
    @Test
    public void updateEmployeeAndPublish_withFailedUpdate_shouldNotCreateOutbox() throws ParseException {
        //given
        Exception expectedException = null;
        String sampleEmail = "aa1ada@bb.com";
        String sampleFullName = "John smith";
        String sampleBirthdayString = "1979-10-14";
        Department sampleDepartment = new Department(10L,"sample name");
        String sampleEmployeeId = "123";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleBirthday = simpleDateFormat.parse(sampleBirthdayString);
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto(sampleEmail,sampleFullName,sampleBirthdayString,sampleDepartment.getId());
        Employee employee = Employee.builder().email(sampleEmail)
                .fullName(sampleFullName).depId(sampleDepartment.getId()).birthday(sampleBirthday).build();

        when(employeeMapper.toModel(employeeRequestDto)).thenReturn(employee);
        when(employeeService.update(employee)).thenThrow(new RuntimeException("Any problem while updating"));
        //when
        try {
            businessFlows.updateEmployeeAndPublish(employeeRequestDto, sampleEmployeeId);
        }catch (Exception e){
            expectedException = e;
        }
        //then
        assertNotNull(expectedException);
        //then
        verify(employeeMapper).toModel(employeeRequestDto);
        verify(employeeService).update(employee);
        verify(employeeMapper,times(0)).toDto(any());
        verify(outboxService,times(0)).create(any(),any());
    }

    @Test
    public void deleteEmployeeAndPublish_withSuccessfulDelete_shouldCreateOutboxRecord() throws ParseException {
        //given
        String sampleEmployeeId = "123";
        //when
        businessFlows.deleteEmployeeAndPublish(sampleEmployeeId);
        //then
        verify(employeeService).remove(sampleEmployeeId);
        verify(outboxService).create(sampleEmployeeId,EventType.DELETE_EVENT);

    }
    @Test
    public void deleteEmployeeAndPublish_withFailedDelete_shouldNotCreateOutboxRecord() throws ParseException {
        //given
        Exception expectedException = null;
        String sampleEmployeeId = "123";
        doThrow(new RuntimeException("Any problem while deleting")).when(employeeService).remove(sampleEmployeeId);
        //when
        try {
            businessFlows.deleteEmployeeAndPublish(sampleEmployeeId);
        }catch(Exception e){
            expectedException = e;
        }
        //then
        assertNotNull(expectedException);
        verify(employeeService).remove(sampleEmployeeId);
        verify(outboxService,times(0)).create(any(),any());
    }
}