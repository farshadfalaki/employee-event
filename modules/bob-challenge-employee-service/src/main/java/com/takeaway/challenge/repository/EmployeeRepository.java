package com.takeaway.challenge.repository;

import com.takeaway.challenge.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query("select emp from Employee emp inner join fetch emp.department")
    List<Employee> findAll();
    @Query("select emp from Employee emp inner join fetch emp.department where emp.id = (:id)")
    Employee findByIdAndEagerFetchDepartment(@Param("id") String id);
}
