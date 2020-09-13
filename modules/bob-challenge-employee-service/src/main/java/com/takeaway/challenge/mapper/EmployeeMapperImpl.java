package com.takeaway.challenge.mapper;

import com.takeaway.challenge.dto.EmployeeRequestDto;
import com.takeaway.challenge.dto.EmployeeResponseDto;
import com.takeaway.challenge.model.Employee;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class EmployeeMapperImpl implements EmployeeMapper{
    @Override
    public Employee toModel(EmployeeRequestDto employeeRequestDto){
        if(employeeRequestDto==null)
            return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday;
        try {
            birthday = simpleDateFormat.parse(employeeRequestDto.getBirthday());

        } catch (ParseException e) {
            throw new RuntimeException("Parse error : " + employeeRequestDto.getBirthday());
        }
        return Employee.builder()
                .email(employeeRequestDto.getEmail())
                .fullName(employeeRequestDto.getFullName())
                .birthday(birthday)
                .depId(employeeRequestDto.getDepId())
                .build();
    }

    @Override
    public EmployeeResponseDto
    toDto(Employee employee) {
        if(employee ==null)
            return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String birthday = simpleDateFormat.format(employee.getBirthday());
        return EmployeeResponseDto.builder()
                .id(employee.getId())
                .email(employee.getEmail())
                .fullName(employee.getFullName())
                .birthday(birthday)
                .department(employee.getDepartment())
                .build();
    }

}
