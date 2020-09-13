package com.takeaway.challenge.controller;

import com.takeaway.challenge.flow.BusinessFlows;
import com.takeaway.challenge.dto.EmployeeRequestDto;
import com.takeaway.challenge.dto.EmployeeResponseDto;
import com.takeaway.challenge.mapper.EmployeeMapper;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper  employeeMapper;
    private BusinessFlows businessFlows;
    @PostMapping
    public ResponseEntity<EmployeeResponseDto> create(@Valid @RequestBody EmployeeRequestDto employeeRequestDto){
        EmployeeResponseDto employeeResponseDto = businessFlows.createEmployeeAndPublish(employeeRequestDto);
        return new ResponseEntity<>(employeeResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getByEmployeeId(@PathVariable String id){
        Optional<Employee> employeeOptional = employeeService.getById(id);
        Employee employee = employeeOptional.orElseThrow(() -> new EntityNotFoundException("id:"+id));
        return new ResponseEntity<>(employeeMapper.toDto(employee),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> update(@Valid @RequestBody EmployeeRequestDto employeeRequestDto, @PathVariable String id){
        EmployeeResponseDto employeeResponseDto = businessFlows.updateEmployeeAndPublish(employeeRequestDto,id);
        return new ResponseEntity<>(employeeResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity remove(@PathVariable String id){
        businessFlows.deleteEmployeeAndPublish(id);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/all")
    public ResponseEntity removeAll(){
        employeeService.removeAll();
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public List<Employee> findAll(){
        return employeeService.findAll();
    }

}
