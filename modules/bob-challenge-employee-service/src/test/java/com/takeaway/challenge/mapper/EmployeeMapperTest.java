package com.takeaway.challenge.mapper;

import com.takeaway.challenge.dto.EmployeeRequestDto;
import com.takeaway.challenge.dto.EmployeeResponseDto;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class EmployeeMapperTest {
    EmployeeMapper employeeMapper = new EmployeeMapperImpl();
    @Test
    public void toModel_withNullInput_shouldReturnNull() {
        assertNull(employeeMapper.toModel(null));
    }

    @Test
    public void toModel_withNotNullInput_shouldMapFields() throws ParseException {
        //given
        String sampleEmail = "aaa@bb.com";
        String sampleFullName = "John smith";
        String sampleBirthdayString = "1979-10-14";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleBirthday = simpleDateFormat.parse(sampleBirthdayString);
        Long sampleDepartmentId = 61L;
        EmployeeRequestDto employeeRequestDto = EmployeeRequestDto.builder().fullName(sampleFullName)
                .email(sampleEmail).depId(sampleDepartmentId).birthday(sampleBirthdayString).build();
        Employee expectedEmployee = Employee.builder().email(sampleEmail)
                .fullName(sampleFullName).depId(sampleDepartmentId).birthday(sampleBirthday).build();
        //when
        Employee actualEmployee = employeeMapper.toModel(employeeRequestDto);
        //then
        assertEquals(expectedEmployee,actualEmployee);
    }

    @Test
    public void toDto_withNullInput_shouldReturnNull() {
        assertNull(employeeMapper.toDto(null));
    }

    @Test
    public void toDto_withNotNullInput_shouldMapFields() throws ParseException {
        //given
        String sampleId = "123-45";
        String sampleEmail = "aaa@bb.com";
        String sampleFullName = "John smith";
        String sampleBirthdayString = "1979-10-14";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleBirthday = simpleDateFormat.parse(sampleBirthdayString);
        Long sampleDepartmentId = 61L;
        Department sampleDepartment = new Department(sampleDepartmentId,"sample dep");

        Employee employee = Employee.builder().id(sampleId).fullName(sampleFullName)
                .email(sampleEmail).department(sampleDepartment).birthday(sampleBirthday).build();
        EmployeeResponseDto expectedEmployeeResponseDto = EmployeeResponseDto.builder()
                .id(sampleId).email(sampleEmail)
                .fullName(sampleFullName).department(sampleDepartment).birthday(sampleBirthdayString).build();
        //when
        EmployeeResponseDto actualEmployeeResponseDto = employeeMapper.toDto(employee);
        //then
        assertEquals(expectedEmployeeResponseDto,actualEmployeeResponseDto);
    }
}