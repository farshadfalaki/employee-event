package com.takeaway.challenge.service;

import com.takeaway.challenge.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    void remove(String id);

    Employee create(Employee employee);

    Employee update(Employee employee);

    Optional<Employee> getById(String id);

    void removeAll();

    List<Employee> findAll();
}
