package com.takeaway.challenge.service;

import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    DepartmentRepository departmentRepository;
    @InjectMocks
    EmployeeServiceImpl employeeService;

    @Test
    public void create_withFullyFilledEmployee_shouldReturnEmployee() throws ParseException {
        //given
        String sampleId = "123-45";
        String sampleEmail = "aaa@bb.com";
        String sampleFullName = "John smith";
        String sampleBirthdayString = "1979-10-14";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleBirthday = simpleDateFormat.parse(sampleBirthdayString);
        Long sampleDepartmentId = 61L;
        Department sampleDepartment = new Department(sampleDepartmentId,"sample dep");

        Employee employee = Employee.builder().fullName(sampleFullName)
                .email(sampleEmail).depId(sampleDepartmentId).birthday(sampleBirthday).build();
        Employee expectedEmployee = Employee.builder().id(sampleId).fullName(sampleFullName)
                .email(sampleEmail).department(sampleDepartment).birthday(sampleBirthday).build();
        when(employeeRepository.save(employee)).thenReturn(expectedEmployee);
        when(departmentRepository.findById(sampleDepartmentId)).thenReturn(Optional.of(sampleDepartment));
        //when
        Employee actualEmployee = employeeService.create(employee);
        //then
        assertEquals(expectedEmployee,actualEmployee);
        verify(employeeRepository).save(employee);
        verify(departmentRepository).findById(sampleDepartmentId);
    }
    @Test
    public void getById_withExistingId_shouldReturnOptionalOfEntity() throws ParseException {
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
        when(employeeRepository.findByIdAndEagerFetchDepartment(sampleId)).thenReturn(employee);
        //when
        Optional<Employee> employeeOptional = employeeService.getById(sampleId);
        //then
        assertEquals(Optional.of(employee),employeeOptional);
        verify(employeeRepository).findByIdAndEagerFetchDepartment(sampleId);

    }
    @Test
    public void getById_withNonExistingId_shouldReturnOptionalOfNull(){
        //given
        String sampleId = "123-45";
        when(employeeRepository.findByIdAndEagerFetchDepartment(sampleId)).thenReturn(null);
        //when
        Optional<Employee> employeeOptional = employeeService.getById(sampleId);
        //then
        assertEquals(Optional.empty(),employeeOptional);
        verify(employeeRepository).findByIdAndEagerFetchDepartment(sampleId);
    }

    @Test
    public void update_withExistingEmployeeId_shouldReturnEmployee() throws ParseException {
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
                .email(sampleEmail).depId(sampleDepartmentId).birthday(sampleBirthday).build();
        Employee expectedEmployee = Employee.builder().id(sampleId).fullName(sampleFullName)
                .email(sampleEmail).department(sampleDepartment).birthday(sampleBirthday).build();
        when(employeeRepository.save(employee)).thenReturn(expectedEmployee);
        when(employeeRepository.getOne(sampleId)).thenReturn(expectedEmployee);
        when(departmentRepository.findById(sampleDepartmentId)).thenReturn(Optional.of(sampleDepartment));
        //when
        Employee actualEmployee = employeeService.update(employee);
        //then
        assertEquals(expectedEmployee,actualEmployee);
        verify(employeeRepository).save(employee);
        verify(employeeRepository).getOne(sampleId);
        verify(departmentRepository).findById(sampleDepartmentId);
    }

    @Test(expected = EntityNotFoundException.class)
    public void update_withNonExistingEmployeeId_shouldThrowEntityNotFoundException() throws ParseException {
        //given
        String sampleId = "123-45";
        String sampleEmail = "aaa@bb.com";
        String sampleFullName = "John smith";
        String sampleBirthdayString = "1979-10-14";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleBirthday = simpleDateFormat.parse(sampleBirthdayString);
        Long sampleDepartmentId = 61L;

        Employee employee = Employee.builder().id(sampleId).fullName(sampleFullName)
                .email(sampleEmail).depId(sampleDepartmentId).birthday(sampleBirthday).build();
        when(employeeRepository.getOne(sampleId)).thenThrow(new EntityNotFoundException());
        //when
        employeeService.update(employee);
        //then
    }

    @Test
    public void delete_withExistingId_shouldReturnVoid(){
        //given
        String sampleId = "123-45";
        //when
        employeeService.remove(sampleId);
        //then
        verify(employeeRepository).deleteById(sampleId);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void delete_withExistingId_shouldThrowEmptyResultDataAccessException(){
        //given
        String sampleId = "123-45";
        doThrow(new EmptyResultDataAccessException(1)).when(employeeRepository).deleteById(sampleId);
        //when
        employeeService.remove(sampleId);
        //then
    }
}