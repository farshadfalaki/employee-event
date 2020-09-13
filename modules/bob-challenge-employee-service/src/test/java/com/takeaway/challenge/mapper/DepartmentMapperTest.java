package com.takeaway.challenge.mapper;

import com.takeaway.challenge.dto.DepartmentDto;
import com.takeaway.challenge.model.Department;
import org.junit.Test;

import static org.junit.Assert.*;
public class DepartmentMapperTest {
    DepartmentMapper departmentMapper =new DepartmentMapperImpl();

    @Test
    public void toModel_withDtoValues_shouldMapToModel(){
        //given
        Long sampleId = 1000L;
        String sampleName = "test name";
        DepartmentDto departmentDto =  DepartmentDto.builder().id(sampleId).name(sampleName).build();
        //when
        Department department = departmentMapper.toModel(departmentDto);
        //then
        assertEquals(sampleId,department.getId());
        assertEquals(sampleName,department.getName());
    }

    @Test
    public void toModel_withNullDto_shouldReturnNull(){
        //given
        DepartmentDto departmentDto = null;
        //when
        Department department = departmentMapper.toModel(departmentDto);
        //then
        assertNull(department);
    }

    @Test
    public void toModel_withNullIdValue_shouldMapToModel(){
        //given
        Long sampleId = null;
        String sampleName = "test name";
        DepartmentDto departmentDto =  DepartmentDto.builder().id(sampleId).name(sampleName).build();
        //when
        Department department = departmentMapper.toModel(departmentDto);
        //then
        assertEquals(sampleId,department.getId());
        assertEquals(sampleName,department.getName());
    }

    @Test
    public void toDto_withModelValues_shouldMapToDto(){
        //given
        Long sampleId = 1000L;
        String sampleName = "test name";
        Department department = new Department(sampleId,sampleName);
        //when
        DepartmentDto departmentDto = departmentMapper.toDto(department);
        //then
        assertEquals(sampleId,departmentDto.getId());
        assertEquals(sampleName,departmentDto.getName());
    }

    @Test
    public void toModel_withNullModel_shouldReturnNull(){
        //given
        Department department = null;
        //when
        DepartmentDto departmentDto = departmentMapper.toDto(department);
        //then
        assertNull(departmentDto);
    }

}