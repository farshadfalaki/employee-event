package com.takeaway.challenge.controller;

import com.takeaway.challenge.dto.DepartmentDto;
import com.takeaway.challenge.mapper.DepartmentMapper;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/department")
@AllArgsConstructor
public class DepartmentController {
    private final DepartmentMapper    departmentMapper;
    private final DepartmentService   departmentService;

    @PostMapping
    ResponseEntity<DepartmentDto> create(@Valid @RequestBody DepartmentDto departmentDto){
        Department department = departmentMapper.toModel(departmentDto);
        department =  departmentService.save(department);
        return new ResponseEntity<>(departmentMapper.toDto(department), HttpStatus.CREATED);
    }
}
