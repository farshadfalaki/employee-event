package com.takeaway.challenge.service;

import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.repository.DepartmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceTest {
    @Mock
    DepartmentRepository departmentRepository;
    @InjectMocks
    DepartmentServiceImpl departmentService ;
    @Test
    public void save_withNameValue_shouldReturnSameNameWithNotNullId(){
        //given
        String sampleName = "test name";
        Long sampleId = 120L;
        Department department = new Department(null,sampleName);
        Department expectedDepartment = new Department(sampleId,sampleName);
        when(departmentRepository.save(department)).thenReturn(expectedDepartment);
        //when
        Department savedDepartment = departmentService.save(department);
        //then
        assertEquals(expectedDepartment,savedDepartment);
        verify(departmentRepository).save(department);
    }
}