package com.takeaway.challenge.service;

import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository   employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public Employee create(Employee employee) {
        Optional<Department> departmentOptional = departmentRepository.findById(employee.getDepId());
        departmentOptional.ifPresent(employee::setDepartment);
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> getById(String id) {
        return Optional.ofNullable(employeeRepository.findByIdAndEagerFetchDepartment(id));
    }

    @Override
    public Employee update(Employee employee) {
        Employee employeeFetched = employeeRepository.getOne(employee.getId());
        log.info("Fetched employee " + employeeFetched);
        Optional<Department> departmentOptional = departmentRepository.findById(employee.getDepId());
        departmentOptional.ifPresent(employee::setDepartment);
        return employeeRepository.save(employee);
    }

    @Override
    public void remove(String id) {
        employeeRepository.deleteById(id);
    }


    @Override
    @Transactional
    public void removeAll() {
        employeeRepository.deleteAll();
    }

    @Override
    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

}
